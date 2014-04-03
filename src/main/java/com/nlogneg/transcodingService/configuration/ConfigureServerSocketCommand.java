package com.nlogneg.transcodingService.configuration;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.request.server.ServerSocketProxy;
import com.nlogneg.transcodingService.utilities.system.ExitSystemCommand;

/**
 * Configures the Server Socket to use for this application
 * 
 * @author anjohnson
 * 
 */
public class ConfigureServerSocketCommand extends SimpleCommand
{
	/**
	 * The notification to send in order to execute this command
	 */
	public static final String ConfigureServerSocket = "ConfigureServerSocket";
	
	private static final Logger Log = LogManager.getLogger(ConfigureServerSocketCommand.class);

	@Override
	public void execute(final INotification notification)
	{
		final ConfigurationFileProxy proxy = (ConfigurationFileProxy) this.getFacade().retrieveProxy(
				ConfigurationFileProxy.PROXY_NAME);
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
			sendNotification(ExitSystemCommand.ExitSystem);
		}
	}
}
