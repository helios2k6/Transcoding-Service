package com.nlogneg.transcodingService.request.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.constants.Notifications;

/**
 * Listens for a new client request and then saves the socket to the socket
 * proxy
 * 
 * @author anjohnson
 * 
 */
public class ListenForNewRequestCommand extends SimpleCommand
{

	private static final Logger Log = LogManager.getLogger(ListenForNewRequestCommand.class);

	@Override
	public void execute(final INotification notification)
	{
		final Facade facade = this.getFacade();
		final ServerSocketProxy serverSocketProxy = (ServerSocketProxy) facade.retrieveProxy(ServerSocketProxy.PROXY_NAME);

		final ServerSocket serverSocket = serverSocketProxy.getServerSocket();

		try
		{
			final Socket clientSocket = serverSocket.accept();
			final SocketProxy socketProxy = (SocketProxy) facade.retrieveProxy(SocketProxy.PROXY_NAME);
			socketProxy.addSocketToQueue(clientSocket);
		} catch (final ClosedChannelException ex)
		{
			Log.info(
					"The server socket has been closed, possibly by another thread.",
					ex);
			return;
		} catch (final IOException ex)
		{
			Log.error(
					"An unknown IO Exception has occurred while listening to the socket.",
					ex);
			this.sendNotification(Notifications.ExitSystem, new Integer(-1));
		}
	}
}
