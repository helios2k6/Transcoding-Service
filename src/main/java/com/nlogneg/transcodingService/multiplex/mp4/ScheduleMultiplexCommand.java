package com.nlogneg.transcodingService.multiplex.mp4;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.multiplex.MultiplexJob;
import com.nlogneg.transcodingService.multiplex.MultiplexTracksRunnable;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * Schedules a multiplexing job
 * @author anjohnson
 *
 */
public final class ScheduleMultiplexCommand extends SimpleCommand implements CompletionHandler<Void, MultiplexJob>{
	private static final Logger Log = LogManager.getLogger(ScheduleMultiplexCommand.class);
	
	public void execute(INotification notification){
		MultiplexJob job = (MultiplexJob)notification.getBody();
		ExecutorProxy proxy = (ExecutorProxy)getFacade().retrieveProxy(ExecutorProxy.PROXY_NAME);
		ExecutorService service = proxy.getService();
		MP4BoxArgumentBuilder builder = new MP4BoxArgumentBuilder();
		MultiplexTracksRunnable runnable = new MultiplexTracksRunnable(job, builder, this);
		
		service.submit(runnable);
	}
	
	@Override
	public void completed(Void result, MultiplexJob attachment){
		Log.info("Multiplex job completed successfully : " + attachment.getDestinationFile());
		sendNotification(Notifications.MultiplexFileCommandSuccessNotificaiton, attachment);
	}

	@Override
	public void failed(Throwable exc, MultiplexJob attachment){
		Log.error("Multiplexing job failed: " + attachment.getDestinationFile());
		sendNotification(Notifications.MultiplexFileCommandFailureNotificaiton, attachment);
	}
}
