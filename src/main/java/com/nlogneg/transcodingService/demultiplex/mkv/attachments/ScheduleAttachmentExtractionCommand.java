package com.nlogneg.transcodingService.demultiplex.mkv.attachments;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * 
 * @author anjohnson
 * 
 */
public final class ScheduleAttachmentExtractionCommand extends SimpleCommand implements CompletionHandler<Void, DemultiplexMKVJob>
{
	private static final Logger Log = LogManager.getLogger(ScheduleAttachmentExtractionCommand.class);

	@Override
	public final void execute(final INotification notification)
	{
		final DemultiplexMKVJob job = (DemultiplexMKVJob) notification.getBody();

		Log.info("Scheduling MKV attachment extraction for: " + job.getMediaFile());

		final ExecutorProxy executorProxy = (ExecutorProxy) this.getFacade().retrieveProxy(
				ExecutorProxy.PROXY_NAME);
		final ServerConfigurationProxy serverConfigurationProxy = (ServerConfigurationProxy) this.getFacade().retrieveProxy(
				ServerConfigurationProxy.PROXY_NAME);
		final Path fontFolder = serverConfigurationProxy.getConfigurationFile().getFontFolder();
		final FontInstaller fontInstaller = FontInstallerFactory.createFontInstaller();

		final ProcessAttachmentRunnable runnable = new ProcessAttachmentRunnable(
				job,
				this,
				fontInstaller,
				fontFolder);
		executorProxy.getService().submit(runnable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.nio.channels.CompletionHandler#completed(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void completed(final Void result, final DemultiplexMKVJob job)
	{
		Log.info("Attachment processing successful for: " + job.getMediaFile());
		this.sendNotification(Notifications.DemultiplexAttachmentSuccess, job);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable,
	 * java.lang.Object)
	 */
	@Override
	public void failed(final Throwable exc, final DemultiplexMKVJob job)
	{
		Log.info("Attachment processing failed for: " + job.getMediaFile());
		this.sendNotification(Notifications.DemultiplexAttachmentFailure, job);
	}
}