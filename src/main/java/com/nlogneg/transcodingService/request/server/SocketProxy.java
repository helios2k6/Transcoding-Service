package com.nlogneg.transcodingService.request.server;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Represents the queue of sockets that must be serviced
 * @author anjohnson
 *
 */
public class SocketProxy extends Proxy{
	public static final String PROXY_NAME = "SocketProxy";
	
	private final BlockingQueue<Socket> sockets = new LinkedBlockingDeque<>();

	/**
	 * Constructs a new SocketProxy
	 */
	public SocketProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Add a socket to the queue of sockets
	 * @param socket The socket to add
	 */
	public void addSocketToQueue(Socket socket){
		sockets.add(socket);
	}
	
	/**
	 * Attempts to take the next socket. 
	 * @return The next socket to service. Null if there aren't any other sockets
	 * to service
	 */
	public Socket getNextSocket(){
		return sockets.poll();
	}
	
	/**
	 * Queries whether or not any sockets need to be serviced
	 * @return
	 */
	public boolean anySocketsToService(){
		return sockets.isEmpty() == false;
	}
}
