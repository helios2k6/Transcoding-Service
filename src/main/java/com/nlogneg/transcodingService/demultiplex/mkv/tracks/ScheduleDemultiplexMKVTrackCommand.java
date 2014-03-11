package com.nlogneg.transcodingService.demultiplex.mkv.tracks;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * Schedules MKV track demultiplexion
 * @author Andrew
 *
 */
public final class ScheduleDemultiplexMKVTrackCommand extends SimpleCommand implements CompletionHandler<Void, DemultiplexMKVJob>{
	private static final Logger Log = LogManager.getLogger(ScheduleDemultiplexMKVTrackCommand.class);
	/* (non-Javadoc)
	 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand#execute(org.puremvc.java.multicore.interfaces.INotification)
	 */
	@Override
	public void execute(INotification notification){
		DemultiplexMKVJob job = (DemultiplexMKVJob)notification.getBody();
		DemultiplexTrackRunnable multiplexer = new DemultiplexTrackRunnable(job, this);
		ExecutorProxy proxy = (ExecutorProxy)getFacade().retrieveProxy(ExecutorProxy.PROXY_NAME);
		ExecutorService service = proxy.getService();
		service.submit(multiplexer);
	}

	@Override
	public void completed(Void result, DemultiplexMKVJob job){
		Log.info("Track demultiplex job succeeded for: " + job.getMediaFile());
		sendNotification(Notifications.DemultiplexTrackCommandSuccessNotification, job);
	}

	@Override
	public void failed(Throwable exc, DemultiplexMKVJob job){
		Log.info("Track demultiplex job failed for: " + job.getMediaFile());
		sendNotification(Notifications.DemultiplexTrackCommandFailureNotification, job);
	}
}
