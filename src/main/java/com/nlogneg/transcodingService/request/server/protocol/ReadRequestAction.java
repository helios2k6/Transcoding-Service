package com.nlogneg.transcodingService.request.server.protocol;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.request.server.protocol.MediaRequestProtocol.State;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

public final class ReadRequestAction implements MediaRequestProtocolVisitor{

	private static final String END_OF_MESSAGE_TOKEN = "$END$";
	private static final Logger Log = LogManager.getLogger(ReadRequestAction.class);
	
	private final InputStream stream;
	
	public ReadRequestAction(InputStream stream)
	{
		this.stream = stream;
	}
	
	@Override
	public void visit(MediaRequestProtocol protocol)
	{
		String nextToken = getNextToken();
		XStream deserializer = SerializerFactory.generateDefaultRequestSerializer();
		try
		{
			Request request = (Request)deserializer.fromXML(nextToken);
			
			if(request == null)
			{
				Log.error("Could not deserialize request string: " + nextToken);
				protocol.setCurrentState(State.RECEIVED_REQUEST_FAILURE);
			}
			else
			{
				Log.info("Deserialized request successfully");
				protocol.setCurrentState(State.RECEIVED_REQUEST_SUCCESS);
				protocol.setRequest(request);
			}
		}
		catch(XStreamException e)
		{
			Log.error("Could not deserialize request string: " + nextToken, e);
		}
	}

	private String getNextToken()
	{
		Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(stream)));
		scanner.useDelimiter(END_OF_MESSAGE_TOKEN);
		
		return scanner.next();
	}
}
