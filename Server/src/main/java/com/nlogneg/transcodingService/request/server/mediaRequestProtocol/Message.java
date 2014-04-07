package com.nlogneg.transcodingService.request.server.mediaRequestProtocol;

import java.io.Serializable;

/**
 * Represents a message associated with the Media Request Protocol
 * @author Andrew
 *
 */
public final class Message implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5062767024352382798L;
	
	private Header header;
	private String payload;
	
	/**
	 * @return the header
	 */
	public Header getHeader()
	{
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(Header header)
	{
		this.header = header;
	}
	/**
	 * @return the payload
	 */
	public String getPayload()
	{
		return payload;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(String payload)
	{
		this.payload = payload;
	}
}
