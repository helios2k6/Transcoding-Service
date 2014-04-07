package com.nlogneg.transcodingService.request.server.mediaRequestProtocol;

import java.io.Serializable;

/**
 * Represents the message header for the Media Request Protocol
 */
public final class Header implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -25513720004386365L;
	
	public static final String RequestType = "REQUEST";
	public static final String AcknowledgmentType = "ACK";
	public static final String RawType = "RAW";
	public static final String StatusType = "STATUS";
	public static final String KeepAliveType = "KEEPALIVE";
	
	private String type;

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	
	/**
	 * Creates an acknowledgement header
	 * @return
	 */
	public static Header createAcknowledgmentHeader()
	{
		Header header = new Header();
		header.setType(AcknowledgmentType);
		
		return header;
	}
}
