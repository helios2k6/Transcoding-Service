package com.nlogneg.transcodingService.request.outgoing;

import java.io.Serializable;

/**
 * Represents the acknowledgement message sent by the server
 * 
 * @author anjohnson
 * 
 */
public final class Acknowledgement implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2122413979486525285L;

	public static final Acknowledgement SUCCESS = new Acknowledgement("SUCCESS");
	public static final Acknowledgement FAILURE = new Acknowledgement("FAILURE");

	private final String status;

	/**
	 * @param status
	 */
	private Acknowledgement(final String status)
	{
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return this.status;
	}
}
