package com.nlogneg.transcodingService.request.server;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Holds the status of the server
 * @author Andrew
 *
 */
public final class ServerStatusProxy extends Proxy
{
	public static final String PROXY_NAME = "ServerStatusProxy";
	
	private volatile boolean isRunning = false;
	
	public ServerStatusProxy()
	{
		super(PROXY_NAME);
	}
	
	/**
	 * Sets the isRunning flag to true
	 */
	public void start()
	{
		isRunning = true;
	}
	
	/**
	 * Sets the isRunning flag to false
	 */
	public void stop()
	{
		isRunning = false;
	}
	
	/**
	 * Gets the isRunning flag
	 * @return
	 */
	public boolean getIsRunning()
	{
		return isRunning;
	}
}
