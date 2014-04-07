package com.nlogneg.transcodingService.demultiplex;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.JobStatus;
import com.nlogneg.transcodingService.MasterJobController;
import com.nlogneg.transcodingService.StatusTuple;
import com.nlogneg.transcodingService.demultiplex.mkv.attachments.ScheduleAttachmentExtractionCommand;
import com.nlogneg.transcodingService.demultiplex.mkv.tracks.ScheduleDemultiplexMKVTrackCommand;

/**
 * The main demultiplexing controller that reports directly to the Super
 * Controller
 * 
 * @author anjohnson
 * 
 */
public class DemultiplexController extends SimpleCommand
{
	/**
	 * The notification that starts this command
	 */
	public static final String StartDemultiplexJob = "StartDemultiplexJob";

	/**
	 * The success signal for track extraction
	 */
	public static final String DemultiplexTrackSuccess = "DemultiplexTrackSuccess";

	/**
	 * The failure signal for track extraction
	 */
	public static final String DemultiplexTrackFailure = "DemultiplexTrackFailure";

	/**
	 * The success signal for attachment success
	 */
	public static final String DemultiplexAttachmentSuccess = "DemultiplexAttachmentSuccess";

	/**
	 * The failure signal for attachment failure
	 */
	public static final String DemultiplexAttachmentFailure = "DemultiplexAttachmentFailure";

	private static final Logger Log = LogManager.getLogger(DemultiplexController.class);
	private static final Map<StatusTuple, Reaction> StateMap = generateStateMap();

	private enum Reaction
	{
		NotifySuccess, NotifyFailure, ScheduleTrack, ScheduleAttachment, NoOp
	}

	private static Map<StatusTuple, Reaction> generateStateMap()
	{
		final Class<JobStatus> clazz = JobStatus.class;
		final JobStatus[] possibleStatuses = clazz.getEnumConstants();

		final Map<StatusTuple, Reaction> stateMap = new HashMap<>();

		for (final JobStatus trackStatus : possibleStatuses)
		{
			for (final JobStatus attachmentStatus : possibleStatuses)
			{
				final StatusTuple tuple = new StatusTuple(
						trackStatus,
						attachmentStatus);

				if ((trackStatus == JobStatus.Failed) || (attachmentStatus == JobStatus.Failed))
				{
					stateMap.put(tuple, Reaction.NotifyFailure);
				}
				else
				{
					// Check in-progres conditions
					if ((trackStatus == JobStatus.InProgress) || (attachmentStatus == JobStatus.InProgress))
					{
						/*
						 * Doesn't matter what anything else is since we know
						 * it's not in a failed state
						 */
						stateMap.put(tuple, Reaction.NoOp);
					}
					else if (trackStatus == JobStatus.Pending)
					{
						// Need to extract tracks
						stateMap.put(tuple, Reaction.ScheduleTrack);
					}
					else if (attachmentStatus == JobStatus.Pending)
					{
						// Need to extract attachments
						stateMap.put(tuple, Reaction.ScheduleAttachment);
					}
					else
					{
						// Looks like we've successfully done everything
						stateMap.put(tuple, Reaction.NotifySuccess);
					}
				}
			}
		}

		return stateMap;
	}

	@Override
	public void execute(final INotification notification)
	{
		this.dispatchMessage(
				notification.getName(),
				(DemultiplexJob) notification.getBody());
	}

	private void dispatchMessage(final String message, final DemultiplexJob job)
	{
		Log.info("Dispatching message for demultiplexing job: " + job.getMediaFile());
		switch (message)
		{
		case StartDemultiplexJob:
			Log.info("Start demultplexing.");
			final DemultiplexJobStatusProxy proxy = this.getStatusProxy();
			proxy.addJob(job);
			this.evaluateJobState(job);
			break;
		case DemultiplexAttachmentFailure:
			Log.info("Handle attachment demultiplexing failure: " + job.getMediaFile());
			this.handleAttachmentFailureMessage(job);
			break;
		case DemultiplexTrackFailure:
			Log.info("Handle track demultiplexing failure: " + job.getMediaFile());
			this.handleTrackFailureMessage(job);
			break;
		case DemultiplexAttachmentSuccess:
			Log.info("Handle attachment demultiplex success: " + job.getMediaFile());
			this.handleAttachmentSuccessMessage(job);
			break;
		case DemultiplexTrackSuccess:
			Log.info("Handle track demultiplex success: " + job.getMediaFile());
			this.handleTrackSuccessMessage(job);
			break;
		default:
			Log.error("Unknown message received by Demultiplex Controller: " + message);
			throw new RuntimeException("Unknown message.");
		}
	}

	private void handleAttachmentSuccessMessage(final DemultiplexJob job)
	{
		final DemultiplexJobStatusProxy proxy = this.getStatusProxy();
		proxy.setAttachmentStatus(job, JobStatus.Finished);

		this.evaluateJobState(job);
	}

	private void handleTrackSuccessMessage(final DemultiplexJob job)
	{
		final DemultiplexJobStatusProxy proxy = this.getStatusProxy();
		proxy.setTrackStatus(job, JobStatus.Finished);

		this.evaluateJobState(job);
	}

	private void handleAttachmentFailureMessage(final DemultiplexJob job)
	{
		final DemultiplexJobStatusProxy proxy = this.getStatusProxy();
		proxy.setAttachmentStatus(job, JobStatus.Failed);

		this.evaluateJobState(job);
	}

	private void handleTrackFailureMessage(final DemultiplexJob job)
	{
		final DemultiplexJobStatusProxy proxy = this.getStatusProxy();
		proxy.setTrackStatus(job, JobStatus.Failed);

		this.evaluateJobState(job);
	}

	private void evaluateJobState(final DemultiplexJob job)
	{
		final DemultiplexJobStatusProxy proxy = this.getStatusProxy();
		final StatusTuple status = proxy.getStatus(job);

		final Reaction reaction = StateMap.get(status);
		switch (reaction)
		{
		case ScheduleTrack:
			this.sendNotification(
					ScheduleDemultiplexMKVTrackCommand.ScheduleTrackDemultiplexJob,
					job);
			break;
		case ScheduleAttachment:
			this.sendNotification(
					ScheduleAttachmentExtractionCommand.ScheduleAttachmentDemultiplexJob,
					job);
			break;
		case NotifySuccess:
			this.notifySuccess(job);
			break;
		case NotifyFailure:
			this.notifyFailure(job);
			break;
		case NoOp:
			// Literally do nothing
			break;
		default:
			throw new RuntimeException(
					"Unknown state for demultiplex controller.");
		}
	}

	private void cleanup(final DemultiplexJob job)
	{
		final DemultiplexJobStatusProxy proxy = this.getStatusProxy();
		proxy.removeJob(job);
	}

	private void notifyFailure(final DemultiplexJob job)
	{
		this.cleanup(job);
		this.sendNotification(MasterJobController.DemultiplexJobFailure, job);
	}

	private void notifySuccess(final DemultiplexJob job)
	{
		this.cleanup(job);
		this.sendNotification(MasterJobController.DemultiplexJobSuccess, job);
	}

	private DemultiplexJobStatusProxy getStatusProxy()
	{
		return (DemultiplexJobStatusProxy) this.getFacade().retrieveProxy(
				DemultiplexJobStatusProxy.PROXY_NAME);
	}
}
