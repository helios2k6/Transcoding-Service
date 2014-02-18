package com.nlogneg.transcodingService.mediaInfo;

import java.io.Serializable;

/**
 * Represents the Track XML section of the MediaInfo XML file
 * @author anjohnson
 *
 */
public abstract class Track implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5913948153707934965L;
	
	private String format;

	/**
	 * Gets the format
	 * @return The format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets the format
	 * @param format The format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
}
