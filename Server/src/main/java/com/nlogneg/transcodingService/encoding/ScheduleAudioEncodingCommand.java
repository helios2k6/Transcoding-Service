package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.encoding.neroAac.NeroAacArgumentBuilder;
import com.nlogneg.transcodingService.utilities.threads.ExecutorServiceProxy;

/**
 * Encodes or processes the audio track of a given Encoding Job
 * 
 * @author anjohnson
 * 
 */
public final class ScheduleAudioEncodingCommand extends SimpleCommand implements CompletionHandler<Void, EncodingJob>
{
	/**
	 * Schedule audio encode
	 */
	public static final String ScheduleAudioEncode = "ScheduleAudioEncode";

	private static final Logger Log = LogManager.getLogger(ScheduleAudioEncodingCommand.class);

	@Override
	public void execute(final INotification notification)
	{
		final EncodingJob job = (EncodingJob) notification.getBody();
		final AudioTrackOption audioOption = job.getAudioTrackOption();

		// Check to see what we do with the audio track
		if (audioOption.getEncodingAction() != EncodingAction.Encode)
		{
			Log.info("Bypassing audio encode for: " + job.getSourceFilePath());
			return;
		}

		Log.info("Scheduling audio encode for: " + job.getSourceFilePath());

		final EncodeAudioRunnable runnable = new EncodeAudioRunnable(
				job,
				this,
				new NeroAacArgumentBuilder());
		final ExecutorServiceProxy proxy = (ExecutorServiceProxy) this.getFacade().retrieveProxy(
				ExecutorServiceProxy.PROXY_NAME);
		proxy.getService().submit(runnable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.nio.channels.CompletionHandler#completed(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void completed(final Void arg0, final EncodingJob job)
	{
		Log.info("Audio encoding successful for media job: " + job.getSourceFilePath());
		this.sendNotification(EncodingController.EncodeAudioSuccess, job);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable,
	 * java.lang.Object)
	 */
	@Override
	public void failed(final Throwable arg0, final EncodingJob job)
	{
		Log.info("Audio encoding failed for media job: " + job.getSourceFilePath());
		this.sendNotification(EncodingController.EncodeAudioFailure, job);
	}
}
