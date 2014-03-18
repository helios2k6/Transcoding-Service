package com.nlogneg.transcodingService.encoding;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.JobStatus;
import com.nlogneg.transcodingService.StatusTuple;
import com.nlogneg.transcodingService.constants.Notifications;

public class EncodingController extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(EncodingController.class);
	private static final Map<StatusTuple, Reaction> StateMap = generateStateMap();
	
	private enum Reaction{
		NotifySuccess,
		NotifyFailure,
		ScheduleVideo,
		ScheduleAudio,
		NoOp
	}
	
	private static Map<StatusTuple, Reaction> generateStateMap(){
		Class<JobStatus> clazz = JobStatus.class;
		JobStatus[] possibleStatuses = clazz.getEnumConstants();

		Map<StatusTuple, Reaction> stateMap = new HashMap<>();

		for(JobStatus videoStatus : possibleStatuses){
			for(JobStatus audioStatus : possibleStatuses){
				StatusTuple tuple = new StatusTuple(videoStatus, audioStatus);

				if(videoStatus == JobStatus.Failed || audioStatus == JobStatus.Failed){
					stateMap.put(tuple, Reaction.NotifyFailure);
				}else{
					//Check in-progres conditions
					if(videoStatus == JobStatus.InProgress || audioStatus == JobStatus.InProgress){
						/*
						 * Doesn't matter what anything else is since we 
						 * know it's not in a failed state
						 */
						stateMap.put(tuple, Reaction.NoOp);
					}else if(videoStatus == JobStatus.Pending){
						//Need to extract tracks
						stateMap.put(tuple, Reaction.ScheduleVideo);
					}else if(audioStatus == JobStatus.Pending){
						//Need to extract attachments
						stateMap.put(tuple, Reaction.ScheduleAudio);
					}else{
						//Looks like we've successfully done everything
						stateMap.put(tuple, Reaction.NotifySuccess);
					}
				}
			}
		}

		return stateMap;
	}
	
	@Override
	public void execute(INotification notification){
		dispatchMessage(notification.getName(), (EncodingJob)notification.getBody());
	}
	
	private void dispatchMessage(String message, EncodingJob job){
		switch(message){
		case Notifications.StartEncodingJob:
			EncodingJobStatusProxy proxy = getStatusProxy();
			proxy.addJob(job);
			evaluateJobStatus(job);
			break;
		case Notifications.EncodeVideoFailure:
			handleVideoFailure(job);
			break;
		case Notifications.EncodeAudioFailure:
			handleAudioFailure(job);
			break;
		case Notifications.EncodeVideoSuccess:
			handleVideoSuccess(job);
			break;
		case Notifications.EncodeAudioSuccess:
			handleAudioSuccess(job);
			break;
		default:
			Log.error("Unknown message received by EncodingController: " + message);
			throw new RuntimeException("Unknown message: " + message);
		}
	}
	
	private void evaluateJobStatus(EncodingJob job){
		EncodingJobStatusProxy proxy = getStatusProxy();
		StatusTuple status = proxy.getStatus(job);
		
		Reaction reaction = StateMap.get(status);
		switch(reaction){
		case ScheduleVideo:
			sendNotification(Notifications.ScheduleVideoEncode, job);
			break;
		case ScheduleAudio:
			sendNotification(Notifications.ScheduleAudioEncode, job);
			break;
		case NotifySuccess:
			notifySuccess(job);
			break;
		case NotifyFailure:
			notifyFailure(job);
			break;
		case NoOp:
			//Literally do nothing
			break;
		default:
			Log.error("Unknown state for EncodingController.");
			throw new RuntimeException("Unknown state of EncodingController: " + reaction);
		}
	}
	
	private void handleVideoSuccess(EncodingJob job){
		EncodingJobStatusProxy proxy = getStatusProxy();
		proxy.setVideoStatus(job, JobStatus.Finished);
		
		evaluateJobStatus(job);
	}
	
	private void handleAudioSuccess(EncodingJob job){
		EncodingJobStatusProxy proxy = getStatusProxy();
		proxy.setAudioStatus(job, JobStatus.Finished);
		
		evaluateJobStatus(job);
	}
	
	private void handleVideoFailure(EncodingJob job){
		EncodingJobStatusProxy proxy = getStatusProxy();
		proxy.setVideoStatus(job, JobStatus.Failed);
		
		evaluateJobStatus(job);
	}
	
	private void handleAudioFailure(EncodingJob job){
		EncodingJobStatusProxy proxy = getStatusProxy();
		proxy.setAudioStatus(job, JobStatus.Failed);
		
		evaluateJobStatus(job);
	}
	
	private void cleanup(EncodingJob job){
		EncodingJobStatusProxy proxy = getStatusProxy();
		proxy.removeJob(job);
	}
	
	private void notifyFailure(EncodingJob job){
		cleanup(job);
		sendNotification(Notifications.EncodingJobFailure, job);
	}
	
	private void notifySuccess(EncodingJob job){
		cleanup(job);
		sendNotification(Notifications.EncodingJobSuccess, job);
	}
	
	private EncodingJobStatusProxy getStatusProxy(){
		return (EncodingJobStatusProxy)getFacade().retrieveProxy(EncodingJobStatusProxy.PROXY_NAME);
	}
}
