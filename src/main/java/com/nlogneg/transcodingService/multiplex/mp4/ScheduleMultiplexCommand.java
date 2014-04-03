package com.nlogneg.transcodingService.multiplex.mp4;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.MasterJobController;
import com.nlogneg.transcodingService.multiplex.MultiplexJob;
import com.nlogneg.transcodingService.multiplex.MultiplexTracksRunnable;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * Schedules a multiplexing job
 * 
 * @author anjohnson
 * 
 */
public final class ScheduleMultiplexCommand extends SimpleCommand implements CompletionHandler<Void, MultiplexJob>
{
	/**
	 * Notification to start schedule a multiplex command
	 */
	public static final String ScheduleMultiplex = "ScheduleMultiplex";
	
	private static final Logger Log = LogManager.getLogger(ScheduleMultiplexCommand.class);

	@Override
	public void execute(final INotification notification)
	{
		final MultiplexJob job = (MultiplexJob) notification.getBody();
		final ExecutorProxy proxy = (ExecutorProxy) this.getFacade().retrieveProxy(
				ExecutorProxy.PROXY_NAME);
		final ExecutorService service = proxy.getService();
		final MP4BoxArgumentBuilder builder = new MP4BoxArgumentBuilder();
		final MultiplexTracksRunnable runnable = new MultiplexTracksRunnable(
				job,
				builder,
				this);

		service.submit(runnable);
	}

	@Override
	public void completed(final Void result, final MultiplexJob attachment)
	{
		Log.info("Multiplex job completed successfully : " + attachment.getDestinationFile());
		this.sendNotification(MasterJobController.MultiplexFileSuccess, attachment);
	}

	@Override
	public void failed(final Throwable exc, final MultiplexJob attachment)
	{
		Log.error("Multiplexing job failed: " + attachment.getDestinationFile());
		this.sendNotification(MasterJobController.MultiplexFileFailure, attachment);
	}
}
