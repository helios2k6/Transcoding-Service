package com.nlogneg.transcodingService.demultiplex;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.JobStatus;
import com.nlogneg.transcodingService.constants.Notifications;

/**
 * The main demultiplexing controller that reports directly to the Super Controller
 * @author anjohnson
 *
 */
public class DemultiplexController extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(DemultiplexController.class);

	private enum Reaction
	{
		NotifySuccess,
		NotifyFailure,
		ScheduleTrack,
		ScheduleAttachment,
		NoOp
	}
	
	private static Map<StatusTuple, Reaction> generateStateMap(){
		Class<JobStatus> clazz = JobStatus.class;
		JobStatus[] possibleStatuses = clazz.getEnumConstants();
		
		Map<StatusTuple, Reaction> stateMap = new HashMap<>();
		
		for(JobStatus trackStatus : possibleStatuses){
			for(JobStatus attachmentStatus : possibleStatuses){
				StatusTuple tuple = new StatusTuple(trackStatus, attachmentStatus);
				
				if(trackStatus == JobStatus.Failed || attachmentStatus == JobStatus.Failed){
					stateMap.put(tuple, Reaction.NotifyFailure);
				}else{
					//TODO: Finish this
				}
			}
		}
		
		return stateMap;
	}
	
	public void execute(INotification notification){
		dispatchMessage(notification.getName(), (DemultiplexJob)notification.getBody());
	}

	private void dispatchMessage(String message, DemultiplexJob job){
		switch(message){
		case Notifications.StartDemultiplexJob:
			DemultiplexJobStatusProxy proxy = getStatusProxy();
			proxy.addJob(job);
			evaluateJobState(job);
			break;
		case Notifications.DemultiplexAttachmentFailure:
		case Notifications.DemultiplexTrackFailure:
			break;
		case Notifications.DemultiplexAttachmentSuccess:
			handleAttachmentSuccessMessage(job);
			break;
		case Notifications.DemultiplexTrackSuccess:
			handleTrackSuccessMessage(job);
			break;
		default:
			Log.error("Unknown message received by Demultiplex Controller: " + message);
			throw new RuntimeException("Unknown message.");
		}
	}
	
	private void handleAttachmentSuccessMessage(DemultiplexJob job){
		DemultiplexJobStatusProxy proxy = getStatusProxy();
		proxy.setAttachmentStatus(job, JobStatus.Finished);
		
		evaluateJobState(job);
	}
	
	private void handleTrackSuccessMessage(DemultiplexJob job){
		DemultiplexJobStatusProxy proxy = getStatusProxy();
		proxy.setTrackStatus(job, JobStatus.Finished);
		
		evaluateJobState(job);
	}
	
	private void handleAttachmentFailureMessage(DemultiplexJob job){
		DemultiplexJobStatusProxy proxy = getStatusProxy();
		proxy.setAttachmentStatus(job, JobStatus.Failed);
		
		evaluateJobState(job);
	}
	
	private void handleTrackFailureMessage(DemultiplexJob job){
		DemultiplexJobStatusProxy proxy = getStatusProxy();
		proxy.setTrackStatus(job, JobStatus.Failed);
		
		evaluateJobState(job);
	}
	
	private void evaluateJobState(DemultiplexJob job){
		DemultiplexJobStatusProxy proxy = getStatusProxy();
		StatusTuple status = proxy.getStatus(job);
		
		//Check out tracks first
		if(status.getTrackStatus() == JobStatus.Pending){
			sendNotification(Notifications.ScheduleDemultiplexJob, job);
		}else if(status.getTrackStatus() == JobStatus.Failed){
			notifyFailure(job);
		}
		
		
	}
	
	private void cleanup(DemultiplexJob job){
		DemultiplexJobStatusProxy proxy = getStatusProxy();
		proxy.removeJob(job);
	}
	
	private void notifyFailure(DemultiplexJob job){
		cleanup(job);
		sendNotification(Notifications.DemultiplexJobFailure, job);
	}
	
	private void notifySuccess(DemultiplexJob job){
		cleanup(job);
		sendNotification(Notifications.DemultiplexJobSuccess, job);
	}
	
	private DemultiplexJobStatusProxy getStatusProxy(){
		return (DemultiplexJobStatusProxy)getFacade().retrieveProxy(DemultiplexJobStatusProxy.PROXY_NAME);
	}
}
