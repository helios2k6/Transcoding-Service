package com.nlogneg.transcodingService.request.outgoing;

import java.io.Serializable;

/**
 * Represents an acknowledgement
 * @author anjohnson
 *
 */
public final class Acknowledge implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3208559031635243818L;
	
	private final AcknowledgementType type;
	private final String reason;
	
	/**
	 * @param type
	 * @param reason
	 */
	public Acknowledge(AcknowledgementType type, String reason) {
		this.type = type;
		this.reason = reason;
	}

	/**
	 * @return the type
	 */
	public AcknowledgementType getType() {
		return type;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
}
