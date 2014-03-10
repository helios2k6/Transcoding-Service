package com.nlogneg.transcodingService.demultiplex.mkv.attachments;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.configuration.ServerConfigurationProxy;
import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.demultiplex.fonts.FontInstaller;
import com.nlogneg.transcodingService.demultiplex.fonts.FontInstallerFactory;
import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * Represents the base class for extracting MKV attachments 
 * @author anjohnson
 *
 */
public final class ScheduleAttachmentExtractionCommand extends SimpleCommand implements CompletionHandler<Void, DemultiplexMKVJob>{
	private static final Logger Log = LogManager.getLogger(ScheduleAttachmentExtractionCommand.class);
	
	public final void execute(INotification notification){
		DemultiplexMKVJob job = (DemultiplexMKVJob)notification.getBody();
		
		Log.info("Scheduling MKV attachment extraction for: " + job.getMediaFile());
		
		ExecutorProxy executorProxy = (ExecutorProxy)getFacade().retrieveProxy(ExecutorProxy.PROXY_NAME);
		ServerConfigurationProxy serverConfigurationProxy = (ServerConfigurationProxy)getFacade().retrieveProxy(ServerConfigurationProxy.PROXY_NAME);
		Path fontFolder = serverConfigurationProxy.getConfigurationFile().getFontFolder();
		FontInstaller fontInstaller = FontInstallerFactory.createFontInstaller();
		
		ProcessAttachmentRunnable runnable = new ProcessAttachmentRunnable(job, this, fontInstaller, fontFolder);
		executorProxy.getService().submit(runnable);
	}

	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#completed(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void completed(Void result, DemultiplexMKVJob job){
		Log.info("Attachment processing successful for: " + job.getMediaFile());
		sendNotification(Notifications.DemultiplexAttachmentCommandSuccessNotification, job);
	}

	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable, java.lang.Object)
	 */
	@Override
	public void failed(Throwable exc, DemultiplexMKVJob job) {
		Log.info("Attachment processing failed for: " + job.getMediaFile());
		sendNotification(Notifications.DemultiplexAttachmentCOmmandFailureNotification, job);
	}
}