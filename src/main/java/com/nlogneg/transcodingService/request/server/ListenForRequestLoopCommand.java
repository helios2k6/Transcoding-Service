package com.nlogneg.transcodingService.request.server;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

/**
 * A super-command that listens for requests and marshals them to another thread
 * for servicing and loops back for more listening
 * @author Andrew
 *
 */
public final class ListenForRequestLoopCommand extends SimpleCommand
{
	@Override
	public void execute(INotification notification)
	{
		ServerStatusProxy status = (ServerStatusProxy)getFacade().retrieveProxy(ServerStatusProxy.PROXY_NAME);
		
		while(status.getIsRunning())
		{
			//Listen for a new request
			sendNotification(ListenForNewRequestCommand.ListenForRequest);
			
			//Once we get a request, spin off another thread to handle it
		}
	}
	
	private void spinOffRequest()
	{
		
	}
}
