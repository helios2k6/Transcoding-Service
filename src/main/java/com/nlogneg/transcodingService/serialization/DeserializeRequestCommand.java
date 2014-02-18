package com.nlogneg.transcodingService.serialization;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.requests.Request;
import com.nlogneg.transcodingService.requests.RequestProxy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

/**
 * Represents the deserialization of a request
 * @author anjohnson
 *
 */
public class DeserializeRequestCommand extends SimpleCommand{

	private static final Logger Log = LogManager.getLogger(DeserializeRequestCommand.class);
	
	private final XStream deserializer;
	
	/**
	 * Constructs a new deserialize request command given the XStream deserializer
	 * @param deserializer The deserializer
	 */
	public DeserializeRequestCommand(XStream deserializer){
		this.deserializer = deserializer;
	}
	
	/**
	 * Constructs the default deserialize request command with the default XStream deserializer
	 */
	public DeserializeRequestCommand(){
		this.deserializer = SerializerFactory.generateDefaultRequestSerializer();
	}
	
	public void Execute(INotification notification){
		Facade facade = getFacade();
		SerializedRequestProxy serializedRequestProxy = (SerializedRequestProxy)facade.retrieveProxy(SerializedRequestProxy.PROXY_NAME);
		String request = serializedRequestProxy.getNextSerializedRequest();
		
		if(request == null){
			Log.info("No requests to deserialize");
			return;
		}
		
		try{
			Request deserializedRequest = (Request)deserializer.fromXML(request);
			RequestProxy requestProxy = (RequestProxy)facade.retrieveProxy(RequestProxy.PROXY_NAME);
			requestProxy.addRequest(deserializedRequest);
		}catch(XStreamException ex){
			Log.error("Unable to deserialize request", ex);
			return;
		}
	}
}
