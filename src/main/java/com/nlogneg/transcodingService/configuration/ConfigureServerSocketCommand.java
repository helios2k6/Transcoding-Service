package com.nlogneg.transcodingService.configuration;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.request.server.ServerSocketProxy;

/**
 * Configures the Server Socket to use for this application
 * @author anjohnson
 *
 */
public class ConfigureServerSocketCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(ConfigureServerSocketCommand.class);
	
	public void execute(INotification notification){
		ServerConfigurationProxy proxy = (ServerConfigurationProxy)getFacade().retrieveProxy(ServerConfigurationProxy.PROXY_NAME);
		int portNumber = proxy.getConfigurationFile().getPortNumber();
		
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			ServerSocketProxy serverSocketProxy = (ServerSocketProxy)getFacade().retrieveProxy(ServerSocketProxy.PROXY_NAME);
			serverSocketProxy.setServerSocket(serverSocket);
		}catch (IOException e){
			Log.fatal("Could not bind to socket: " + portNumber);
			sendNotification(Notifications.ExitSystem);
		}
	}
}
