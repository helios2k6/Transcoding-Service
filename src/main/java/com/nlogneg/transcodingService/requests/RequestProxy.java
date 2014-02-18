package com.nlogneg.transcodingService.requests;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Represents the proxy object for all Requests
 * @author anjohnson
 *
 */
public class RequestProxy extends Proxy {

	public static final String PROXY_NAME = "Request Proxy";
	private static final Logger Log = LogManager.getLogger(RequestProxy.class);
	
	private final BlockingQueue<Request> requests;
	
	/**
	 * Constructs a new Request Proxy
	 */
	public RequestProxy() {
		super(PROXY_NAME);
		requests = new LinkedBlockingDeque<Request>();
	}
	
	/**
	 * Adds a new request to the map of Requests that this program has received
	 * @param request The request to add
	 */
	public void addRequest(Request request) {
		if(request == null) {
			Log.error("Request could not be added. Request is null");
			return;
		}
		
		requests.add(request);
	}
}
