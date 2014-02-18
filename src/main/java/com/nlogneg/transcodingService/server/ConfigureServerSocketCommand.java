package com.nlogneg.transcodingService.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.configuration.ServerConfiguration;
import com.nlogneg.transcodingService.configuration.ServerConfigurationProxy;
import com.nlogneg.transcodingService.constants.Notifications;

/**
 * Configures the server socket proxy
 * @author anjohnson
 *
 */
public class ConfigureServerSocketCommand extends SimpleCommand{

	private static final Logger Log = LogManager.getLogger(ConfigureServerSocketCommand.class);
	
	@Override
	public void execute(INotification notification){
		Facade facade = getFacade();
		ServerConfigurationProxy proxy = (ServerConfigurationProxy)facade.retrieveProxy(ServerConfigurationProxy.PROXY_NAME);
		ServerConfiguration configuration = proxy.getServerConfiguration();

		try{
			ServerSocket socket = new ServerSocket(configuration.getPortNumber());
			ServerSocketProxy serverSocketProxy = (ServerSocketProxy)facade.retrieveProxy(ServerSocketProxy.PROXY_NAME);
			serverSocketProxy.setServerSocket(socket);
		}catch (IOException e){
			Log.error("Could not create server socket.", e);
			sendNotification(Notifications.ExitSystemCommandNotification, new Integer(-1));
			return;
		}
	}
}
