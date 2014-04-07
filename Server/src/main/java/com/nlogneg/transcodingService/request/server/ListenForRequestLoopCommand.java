package com.nlogneg.transcodingService.request.server;

import java.util.concurrent.ExecutorService;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.utilities.threads.ExecutorServiceProxy;

/**
 * A super-command that listens for requests and marshals them to another thread
 * for servicing and loops back for more listening
 * 
 * @author Andrew
 * 
 */
public final class ListenForRequestLoopCommand extends SimpleCommand
{
	@Override
	public void execute(final INotification notification)
	{
		final ServerStatusProxy status = (ServerStatusProxy) this.getFacade().retrieveProxy(
				ServerStatusProxy.PROXY_NAME);

		while (status.getIsRunning())
		{
			// Listen for a new request
			this.sendNotification(ListenForNewRequestCommand.ListenForRequest);

			// Once we get a request, spin off another thread to handle it
			this.spinOffRequest();
		}
	}

	private void spinOffRequest()
	{
		final ExecutorServiceProxy proxy = (ExecutorServiceProxy) this.getFacade().retrieveProxy(
				ExecutorServiceProxy.PROXY_NAME);
		final ExecutorService service = proxy.getService();
		service.submit(new Runnable()
		{
			@Override
			public void run()
			{
			}
		});
	}
}
