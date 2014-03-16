package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.encoding.ffmpeg.FFMPEGVideoDecodingArgumentBuilder;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * Encodes a media file
 * @author anjohnson
 *
 */
public final class ScheduleVideoEncodeCommand extends SimpleCommand implements CompletionHandler<Void, EncodingJob>{
	private static final Logger Log = LogManager.getLogger(ScheduleVideoEncodeCommand.class);
	
	public final void execute(INotification notification){
		Log.info("Scheduling encoder job");
		
		EncodingJob job = (EncodingJob)notification.getBody();
		DecoderArgumentBuilder decoderBuilder = new FFMPEGVideoDecodingArgumentBuilder();
		EncoderArgumentBuilder encoderBuilder = new X264EncodingArgumentBuilder();
		ExecutorProxy proxy = (ExecutorProxy)getFacade().retrieveProxy(ExecutorProxy.PROXY_NAME);
		ExecutorService service = proxy.getService();
		
		EncodeVideoRunnable encoder = new EncodeVideoRunnable(job, decoderBuilder, encoderBuilder, this, service);
		service.submit(encoder);
	}
	
	
	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#completed(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void completed(Void arg0, EncodingJob job){
		sendNotification(Notifications.EncodeVideoCommandSuccessNotification, job);
	}

	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable, java.lang.Object)
	 */
	@Override
	public void failed(Throwable arg0, EncodingJob job){
		sendNotification(Notifications.EncodeVideoCommandFailureNotification, job);;
	}
}
