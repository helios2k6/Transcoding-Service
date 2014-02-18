package com.nlogneg.transcodingService.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

/**
 * Attempts to service one request 
 * @author anjohnson
 *
 */
public class ServiceRequestCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(ServiceRequestCommand.class);
	
	public void execute(INotification notification){
		Log.info("Servicing request");
		
		Facade facade = getFacade();
		SocketProxy socketProxy = (SocketProxy)facade.retrieveProxy(SocketProxy.PROXY_NAME);
		Socket request = socketProxy.getNextSocket();
		
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
		
		try{
			request.close();
		}catch (IOException e){
			Log.error("Could not close socket.", e);
			return;
		}
	}
	
	/**
	 * Reads in all the input from a Socket
	 * @param socket The socket to read from
	 * @return The String payload from the socket
	 */
	private static String readFromSocket(Socket socket){
		try(BufferedReader reader = new  BufferedReader(new InputStreamReader(socket.getInputStream()));){
			StringBuffer stringBuffer = new StringBuffer();
			String currentLine = reader.readLine();
			
			//Read in all the strings
			while(currentLine != null){
				stringBuffer.append(currentLine);
				currentLine = reader.readLine();
			}
			
			return stringBuffer.toString();
		}catch (IOException e){
			Log.error("Could not read from socket.", e);
		}
		
		return null;
	}
}
