package com.nlogneg.transcodingService.demultiplex.mkv.tracks;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * Schedules MKV track demultiplexion
 * 
 * @author Andrew
 * 
 */
public final class ScheduleDemultiplexMKVTrackCommand extends SimpleCommand implements CompletionHandler<Void, DemultiplexMKVJob>
{
	private static final Logger Log = LogManager.getLogger(ScheduleDemultiplexMKVTrackCommand.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.puremvc.java.multicore.patterns.command.SimpleCommand#execute(org
	 * .puremvc.java.multicore.interfaces.INotification)
	 */
	@Override
	public void execute(final INotification notification)
	{
		final DemultiplexMKVJob job = (DemultiplexMKVJob) notification.getBody();
		final DemultiplexTrackRunnable multiplexer = new DemultiplexTrackRunnable(
				job,
				this);
		final ExecutorProxy proxy = (ExecutorProxy) this.getFacade().retrieveProxy(
				ExecutorProxy.PROXY_NAME);
		final ExecutorService service = proxy.getService();
		service.submit(multiplexer);
	}

	@Override
	public void completed(final Void result, final DemultiplexMKVJob job)
	{
		Log.info("Track demultiplex job succeeded for: " + job.getMediaFile());
		this.sendNotification(Notifications.DemultiplexTrackSuccess, job);
	}

	@Override
	public void failed(final Throwable exc, final DemultiplexMKVJob job)
	{
		Log.info("Track demultiplex job failed for: " + job.getMediaFile());
		this.sendNotification(Notifications.DemultiplexTrackFailure, job);
	}
}
