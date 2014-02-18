package com.nlogneg.transcodingService.serialization;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Holds all of the serialized requests that come in from clients
 * @author anjohnson
 *
 */
public class SerializedRequestProxy extends Proxy{
	public static final String PROXY_NAME = "SerializedRequestProxy";
	
	private final BlockingQueue<String> serializedRequests;
	
	public SerializedRequestProxy(){
		super(PROXY_NAME);
		serializedRequests = new LinkedBlockingDeque<String>();
	}
	
	/**
	 * Adds a new serialized request to the proxy
	 * @param request The serialized request
	 */
	public void addSerializedRequest(String request){
		serializedRequests.add(request);
	}
	
	/**
	 * Removes the next serialized request
	 * @return The serialized request or null if there isn't any
	 */
	public String getNextSerializedRequest(){
		return serializedRequests.poll();
	}
}
