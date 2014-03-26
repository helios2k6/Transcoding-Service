package com.nlogneg.transcodingService.configuration;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.request.server.ServerSocketProxy;

/**
 * Configures the Server Socket to use for this application
 * 
 * @author anjohnson
 * 
 */
public class ConfigureServerSocketCommand extends SimpleCommand
{
	private static final Logger Log = LogManager.getLogger(ConfigureServerSocketCommand.class);

	@Override
	public void execute(final INotification notification)
	{
		final ServerConfigurationProxy proxy = (ServerConfigurationProxy) this.getFacade().retrieveProxy(
				ServerConfigurationProxy.PROXY_NAME);
		final int portNumber = proxy.getConfigurationFile().getPortNumber();

		try
		{
			final ServerSocket serverSocket = new ServerSocket(portNumber);
			final ServerSocketProxy serverSocketProxy = (ServerSocketProxy) this.getFacade().retrieveProxy(
					ServerSocketProxy.PROXY_NAME);
			serverSocketProxy.setServerSocket(serverSocket);
		}
		catch (final IOException e)
		{
			Log.fatal("Could not bind to socket: " + portNumber);
			this.sendNotification(Notifications.ExitSystem);
		}
	}
}
