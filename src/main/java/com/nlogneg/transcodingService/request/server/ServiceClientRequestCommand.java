package com.nlogneg.transcodingService.request.server;

import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Services a client request
 * @author anjohnson
 *
 */
public final class ServiceClientRequestCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(ServiceClientRequestCommand.class);
	
	/* (non-Javadoc)
	 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand#execute(org.puremvc.java.multicore.interfaces.INotification)
	 */
	@Override
	public void execute(INotification notification) {
		SocketProxy proxy = (SocketProxy)getFacade().retrieveProxy(SocketProxy.PROXY_NAME);
		Optional<Socket> socketOptional = proxy.getNextSocket();
		
		if(socketOptional.isNone())
		{
			return;
		}
		
		Socket socket = socketOptional.getValue();
		Optional<String> socketRequestContents = readFromSocket(socket);
		
		if(socketRequestContents.isNone())
		{
			
		}
		
	}
	
	/**
	 * Reads in all the input from a Socket
	 * 
	 * @param socket
	 *            The socket to read from
	 * @return The String payload from the socket
	 */
	private static Optional<String> readFromSocket(final Socket socket)
	{
		try
		{
			return Optional.make(InputStreamUtilities.readInputStreamToEnd(socket.getInputStream()));
		}
		catch (final IOException exception)
		{
			Log.error("Could not read from socket", exception);
		}

		return Optional.none();
	}
}
