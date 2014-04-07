package com.nlogneg.transcodingService.utilities.threads;

import java.util.concurrent.ExecutorService;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * The holder of all executor services
 * 
 * @author anjohnson
 * 
 */
public final class ExecutorServiceProxy extends Proxy
{
	public static final String PROXY_NAME = "ExecutorServiceProxy";

	private ExecutorService service;

	public ExecutorServiceProxy()
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
