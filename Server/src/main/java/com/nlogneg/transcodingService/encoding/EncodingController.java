package com.nlogneg.transcodingService.encoding;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.JobStatus;
import com.nlogneg.transcodingService.MasterJobController;
import com.nlogneg.transcodingService.StatusTuple;

public final class EncodingController extends SimpleCommand
{
	/**
	 * The notification that starts an encoding job
	 */
	public static final String StartEncodingJob = "StartEncodingJob";

	/**
	 * The success signal for video encoding
	 */
	public static final String EncodeVideoSuccess = "EncodeVideoSuccess";

	/**
	 * The failure signal for video encoding
	 */
	public static final String EncodeVideoFailure = "EncodeVideoFailure";

	/**
	 * The success signal for the audio encoding
	 */
	public static final String EncodeAudioSuccess = "EncodeAudioSuccess";

	/**
	 * The failure signal for audio encoding
	 */
	public static final String EncodeAudioFailure = "EncodeAudioFailure";

	private static final Logger Log = LogManager.getLogger(EncodingController.class);
	private static final Map<StatusTuple, Reaction> StateMap = generateStateMap();

	private enum Reaction
	{
		NotifySuccess, NotifyFailure, ScheduleVideo, ScheduleAudio, NoOp
	}

	private static Map<StatusTuple, Reaction> generateStateMap()
	{
		final Class<JobStatus> clazz = JobStatus.class;
		final JobStatus[] possibleStatuses = clazz.getEnumConstants();

		final Map<StatusTuple, Reaction> stateMap = new HashMap<>();

		for (final JobStatus videoStatus : possibleStatuses)
		{
			for (final JobStatus audioStatus : possibleStatuses)
			{
				final StatusTuple tuple = new StatusTuple(
						videoStatus,
						audioStatus);

				if ((videoStatus == JobStatus.Failed) || (audioStatus == JobStatus.Failed))
				{
					stateMap.put(tuple, Reaction.NotifyFailure);
				}
				else
				{
					// Check in-progres conditions
					if ((videoStatus == JobStatus.InProgress) || (audioStatus == JobStatus.InProgress))
					{
						/*
						 * Doesn't matter what anything else is since we know
						 * it's not in a failed state
						 */
						stateMap.put(tuple, Reaction.NoOp);
					}
					else if (videoStatus == JobStatus.Pending)
					{
						// Need to extract tracks
						stateMap.put(tuple, Reaction.ScheduleVideo);
					}
					else if (audioStatus == JobStatus.Pending)
					{
						// Need to extract attachments
						stateMap.put(tuple, Reaction.ScheduleAudio);
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
				(EncodingJob) notification.getBody());
	}

	private void dispatchMessage(final String message, final EncodingJob job)
	{
		switch (message)
		{
		case StartEncodingJob:
			final EncodingJobStatusProxy proxy = this.getStatusProxy();
			proxy.addJob(job);
			this.evaluateJobStatus(job);
			break;
		case EncodeVideoFailure:
			this.handleVideoFailure(job);
			break;
		case EncodeAudioFailure:
			this.handleAudioFailure(job);
			break;
		case EncodeVideoSuccess:
			this.handleVideoSuccess(job);
			break;
		case EncodeAudioSuccess:
			this.handleAudioSuccess(job);
			break;
		default:
			Log.error("Unknown message received by EncodingController: " + message);
			throw new RuntimeException("Unknown message: " + message);
		}
	}

	private void evaluateJobStatus(final EncodingJob job)
	{
		final EncodingJobStatusProxy proxy = this.getStatusProxy();
		final StatusTuple status = proxy.getStatus(job);

		final Reaction reaction = StateMap.get(status);
		switch (reaction)
		{
		case ScheduleVideo:
			this.sendNotification(
					ScheduleVideoEncodeCommand.ScheduleVideoEncode,
					job);
			break;
		case ScheduleAudio:
			this.sendNotification(
					ScheduleAudioEncodingCommand.ScheduleAudioEncode,
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
			Log.error("Unknown state for EncodingController.");
			throw new RuntimeException(
					"Unknown state of EncodingController: " + reaction);
		}
	}

	private void handleVideoSuccess(final EncodingJob job)
	{
		final EncodingJobStatusProxy proxy = this.getStatusProxy();
		proxy.setVideoStatus(job, JobStatus.Finished);

		this.evaluateJobStatus(job);
	}

	private void handleAudioSuccess(final EncodingJob job)
	{
		final EncodingJobStatusProxy proxy = this.getStatusProxy();
		proxy.setAudioStatus(job, JobStatus.Finished);

		this.evaluateJobStatus(job);
	}

	private void handleVideoFailure(final EncodingJob job)
	{
		final EncodingJobStatusProxy proxy = this.getStatusProxy();
		proxy.setVideoStatus(job, JobStatus.Failed);

		this.evaluateJobStatus(job);
	}

	private void handleAudioFailure(final EncodingJob job)
	{
		final EncodingJobStatusProxy proxy = this.getStatusProxy();
		proxy.setAudioStatus(job, JobStatus.Failed);

		this.evaluateJobStatus(job);
	}

	private void cleanup(final EncodingJob job)
	{
		final EncodingJobStatusProxy proxy = this.getStatusProxy();
		proxy.removeJob(job);
	}

	private void notifyFailure(final EncodingJob job)
	{
		this.cleanup(job);
		this.sendNotification(MasterJobController.EncodingJobFailure, job);
	}

	private void notifySuccess(final EncodingJob job)
	{
		this.cleanup(job);
		this.sendNotification(MasterJobController.EncodingJobSuccess, job);
	}

	private EncodingJobStatusProxy getStatusProxy()
	{
		return (EncodingJobStatusProxy) this.getFacade().retrieveProxy(
				EncodingJobStatusProxy.PROXY_NAME);
	}
}
