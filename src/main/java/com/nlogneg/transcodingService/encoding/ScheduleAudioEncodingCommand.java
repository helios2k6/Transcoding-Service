package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.encoding.neroAac.NeroAacArgumentBuilder;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * Encodes or processes the audio track of a given Encoding Job
 * @author anjohnson
 *
 */
public class ScheduleAudioEncodingCommand extends SimpleCommand implements CompletionHandler<Void, EncodingJob>{
	
	private static final Logger Log = LogManager.getLogger(ScheduleAudioEncodingCommand.class);
	
	public void execute(INotification notification){
		EncodingJob job = (EncodingJob)notification.getBody();
		AudioTrackOption audioOption = job.getAudioTrackOption();
		
		//Check to see what we do with the audio track
		if(audioOption.getEncodingAction() != EncodingAction.Encode){
			Log.info("Bypassing audio encode for: " + job.getSourceFilePath());
			return;
		}
		
		Log.info("Scheduling audio encode for: " + job.getSourceFilePath());
		
		EncodeAudioRunnable runnable = new EncodeAudioRunnable(job, this, new NeroAacArgumentBuilder());
		ExecutorProxy proxy = (ExecutorProxy)getFacade().retrieveProxy(ExecutorProxy.PROXY_NAME);
		proxy.getService().submit(runnable);
	}
	
	
	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#completed(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void completed(Void arg0, EncodingJob job){
		Log.info("Audio encoding successful for media job: " + job.getSourceFilePath());
		sendNotification(Notifications.EncodeAudioSuccess, job);
	}

	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable, java.lang.Object)
	 */
	@Override
	public void failed(Throwable arg0, EncodingJob job){
		Log.info("Audio encoding failed for media job: " + job.getSourceFilePath());
		sendNotification(Notifications.EncodeAudioFailure, job);
	}
}
