package com.nlogneg.transcodingService.request.server;

import java.net.ServerSocket;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Proxy class for the server socket
 * 
 * @author anjohnson
 * 
 */
public class ServerSocketProxy extends Proxy
{
	public static final String PROXY_NAME = "ServerSocketProxy";

	private ServerSocket serverSocket;

	/**
	 * Constructs a new server socket proxy
	 */
	public ServerSocketProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * Gets the server socket
	 * 
	 * @return The server socket
	 */
	public ServerSocket getServerSocket()
	{
		return this.serverSocket;
	}

	/**
	 * Sets the server socket
	 * 
	 * @param serverSocket
	 *            The server socket
	 */
	public void setServerSocket(final ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
	}
}
