package com.nlogneg.transcodingService.mediaInfo;

public abstract class MediaTrack extends Track{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8563336050612487821L;

	private String formatInfo;
	private String codecID;
	
	/**
	 * Get the format info
	 * @return The format info
	 */
	public String getFormatInfo() {
		return formatInfo;
	}
	
	/**
	 * Set the format info
	 * @param formatInfo The format info
	 */
	public void setFormatInfo(String formatInfo) {
		this.formatInfo = formatInfo;
	}
	
	/**
	 * Get the codec ID
	 * @return The codec ID
	 */
	public String getCodecID() {
		return codecID;
	}
	
	/**
	 * Set the codec ID
	 * @param codecID The codec ID
	 */
	public void setCodecID(String codecID) {
		this.codecID = codecID;
	}
	
	
}
