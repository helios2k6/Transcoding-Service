package com.nlogneg.transcodingService.request.incoming;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the proxy object for all Requests
 * 
 * @author anjohnson
 * 
 */
public final class RequestProxy extends Proxy
{

	public static final String PROXY_NAME = "Request Proxy";

	private final BlockingQueue<Request> requests = new LinkedBlockingDeque<>();

	/**
	 * Constructs a new Request Proxy
	 */
	public RequestProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * Adds a new request to the map of Requests that this program has received
	 * 
	 * @param request
	 *            The request to add
	 */
	public void addRequest(final Request request)
	{
		this.requests.add(request);
	}

	/**
	 * Gets the next request
	 * 
	 * @return An optional representing the next Optional
	 */
	public Optional<Request> getNextRequest()
	{
		return Optional.make(this.requests.poll());
	}
}
