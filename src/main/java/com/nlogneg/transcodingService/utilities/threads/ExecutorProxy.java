package com.nlogneg.transcodingService.utilities.threads;

import java.util.concurrent.ExecutorService;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * The holder of all executor services
 * 
 * @author anjohnson
 * 
 */
public final class ExecutorProxy extends Proxy
{
	public static final String PROXY_NAME = "";

	private ExecutorService service;

	public ExecutorProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * @return the service
	 */
	public ExecutorService getService()
	{
		return this.service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(final ExecutorService service)
	{
		this.service = service;
	}
}
