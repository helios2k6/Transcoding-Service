package com.nlogneg.transcodingService.demultiplex;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Contains the Demultiplex Job Status Board
 * @author Andrew
 *
 */
public final class DemultiplexJobStatusBoardProxy extends Proxy{
	public static final String PROXY_NAME = "DemultiplexJobStatusBoardProxy";
	
	private final DemultiplexStatusBoard statusBoard = new DemultiplexStatusBoard();
	
	public DemultiplexJobStatusBoardProxy(){
		super(PROXY_NAME);
	}

	/**
	 * @return the statusBoard
	 */
	public DemultiplexStatusBoard getStatusBoard() {
		return statusBoard;
	}
}
