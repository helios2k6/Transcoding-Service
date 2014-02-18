package com.nlogneg.transcodingService.requests;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Represents the proxy object for all Requests
 * @author anjohnson
 *
 */
public class RequestProxy extends Proxy {

	public static final String PROXY_NAME = "Request Proxy";
	
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
		requests.add(request);
	}
	
	/**
	 * Gets the next requeset
	 * @return The request or null if there aren't any more
	 */
	public Request getNextRequest(){
		return requests.poll();
	}
}
