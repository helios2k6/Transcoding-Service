package com.nlogneg.transcodingService.request.outgoing;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;

/**
 * Responses to a client with the specified acknowledgment and closes the socket
 * @author anjohnson
 *
 */
public final class RespondToClientCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(RespondToClientCommand.ResponseTuple.class);
	
	public final class ResponseTuple{
		private final Acknowledgement acknowledgement;
		private final Socket socket;
		/**
		 * @param acknowledgement
		 * @param socket
		 */
		public ResponseTuple(Acknowledgement acknowledgement, Socket socket) {
			this.acknowledgement = acknowledgement;
			this.socket = socket;
		}
	}
	
	public void execute(INotification notification){
		ResponseTuple responseTuple = (ResponseTuple)notification.getBody();
		
		Socket socket = responseTuple.socket;
		Acknowledgement ack = responseTuple.acknowledgement;
		
		XStream serializer = SerializerFactory.getAcknowledgementSerializer();
		
		String serializedResponse = serializer.toXML(ack);
		
		try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);){
			writer.print(serializedResponse);
		}catch(IOException e){
			Log.error("Could not send response back to client.", e);
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				Log.error("Could not close socket.", e);
			}
		}
	}
}
