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
	private static final Map<StatusTuple, Reaction> StateMap = generateStateMap();

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
					//Check in-progres conditions
					if(trackStatus == JobStatus.InProgress || attachmentStatus == JobStatus.InProgress){
						/*
						 * Doesn't matter what anything else is since we 
						 * know it's not in a failed state
						 */
						stateMap.put(tuple, Reaction.NoOp);
					}else if(trackStatus == JobStatus.Pending){
						//Need to extract tracks
						stateMap.put(tuple, Reaction.ScheduleTrack);
					}else if(attachmentStatus == JobStatus.Pending){
						//Need to extract attachments
						stateMap.put(tuple, Reaction.ScheduleAttachment);
					}else{
						//Looks like we've successfully done everything
						stateMap.put(tuple, Reaction.NotifySuccess);
					}
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
			handleAttachmentFailureMessage(job);
			break;
		case Notifications.DemultiplexTrackFailure:
			handleTrackFailureMessage(job);
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

		Reaction reaction = StateMap.get(status);
		switch(reaction){
		case ScheduleTrack:
			break;
		case ScheduleAttachment:
			sendNotification(Notifications.ScheduleDemultiplexJob, job);
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
			throw new RuntimeException("Unknown state for demultiplex controller.");
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
