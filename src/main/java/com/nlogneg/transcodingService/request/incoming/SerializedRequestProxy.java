package com.nlogneg.transcodingService.request.incoming;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Holds all of the serialized requests that come in from clients
 * 
 * @author anjohnson
 * 
 */
public class SerializedRequestProxy extends Proxy
{
	public static final String PROXY_NAME = "SerializedRequestProxy";

	private final BlockingQueue<String> serializedRequests = new LinkedBlockingDeque<>();

	public SerializedRequestProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * Adds a new serialized request to the proxy
	 * 
	 * @param request
	 *            The serialized request
	 */
	public void addSerializedRequest(final String request)
	{
		this.serializedRequests.add(request);
	}

	/**
	 * Removes the next serialized request
	 * 
	 * @return An optional representing the next serialized request
	 */
	public Optional<String> getNextSerializedRequest()
	{
		return Optional.make(this.serializedRequests.poll());
	}
}
