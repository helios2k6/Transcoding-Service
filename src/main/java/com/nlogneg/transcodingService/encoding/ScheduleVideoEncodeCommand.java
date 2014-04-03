package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.encoding.ffmpeg.FFMPEGVideoDecodingArgumentBuilder;
import com.nlogneg.transcodingService.utilities.threads.ExecutorServiceProxy;

/**
 * Encodes a media file
 * 
 * @author anjohnson
 * 
 */
public final class ScheduleVideoEncodeCommand extends SimpleCommand implements CompletionHandler<Void, EncodingJob>
{
	/**
	 * Schedule video encode
	 */
	public static final String ScheduleVideoEncode = "ScheduleVideoEncode";
	
	private static final Logger Log = LogManager.getLogger(ScheduleVideoEncodeCommand.class);

	@Override
	public final void execute(final INotification notification)
	{
		Log.info("Scheduling encoder job");

		final EncodingJob job = (EncodingJob) notification.getBody();
		final DecoderArgumentBuilder decoderBuilder = new FFMPEGVideoDecodingArgumentBuilder();
		final EncoderArgumentBuilder encoderBuilder = new X264EncodingArgumentBuilder();
		final ExecutorServiceProxy proxy = (ExecutorServiceProxy) this.getFacade().retrieveProxy(
				ExecutorServiceProxy.PROXY_NAME);
		final ExecutorService service = proxy.getService();

		final EncodeVideoRunnable encoder = new EncodeVideoRunnable(
				job,
				decoderBuilder,
				encoderBuilder,
				this,
				service);
		service.submit(encoder);
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
		this.sendNotification(EncodingController.EncodeVideoSuccess, job);
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
		this.sendNotification(EncodingController.EncodeVideoFailure, job);
		;
	}
}
