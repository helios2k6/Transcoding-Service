package com.nlogneg.transcodingService.request.server;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.request.incoming.SerializedRequestProxy;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;

/**
 * Attempts to service one request 
 * @author anjohnson
 *
 */
public class ReadClientRequestFromSocketCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(ReadClientRequestFromSocketCommand.class);
	
	public void execute(INotification notification){
		Log.info("Servicing request");
		
		Socket request = (Socket)notification.getBody();
		
		if(request == null){
			Log.info("No requests to service");
			//If there aren't any requests to service, do nothing
			return;
		}
		
		String requestPayload = readFromSocket(request);
		if(requestPayload == null){
			Log.error("Payload was null.");
			return;
		}
		
		SerializedRequestProxy serializedRequestProxy = (SerializedRequestProxy)getFacade().retrieveProxy(SerializedRequestProxy.PROXY_NAME);
		serializedRequestProxy.addSerializedRequest(requestPayload);
	}
	
	/**
	 * Reads in all the input from a Socket
	 * @param socket The socket to read from
	 * @return The String payload from the socket
	 */
	private static String readFromSocket(Socket socket){
		try{
			return InputStreamUtilities.readInputStreamToEnd(socket.getInputStream());
		}catch(IOException exception){
			Log.error("Could not read from socket", exception);
		}
		
		return null;
	}
}
