package com.nlogneg.transcodingService.mediaInfo;

public final class VideoTrack extends MediaTrack{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8828400707334082736L;

	private String width;
	private String height;
	private String displayAspectRatio;
	private String frameRateMode;
	private String frameRate;
	
	/**
	 * Get the width
	 * @return The width
	 */
	public String getWidth() {
		return width;
	}
	
	/**
	 * Set the width
	 * @param width The width
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	
	/**
	 * Get the height
	 * @return The height
	 */
	public String getHeight() {
		return height;
	}
	
	/**
	 * Set the height
	 * @param height The height
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	
	/**
	 * Get the display aspect ratio
	 * @return The display aspect ratio
	 */
	public String getDisplayAspectRatio() {
		return displayAspectRatio;
	}
	
	/**
	 * Set the display aspect ratio
	 * @param displayAspectRatio The display aspect ratio
	 */
	public void setDisplayAspectRatio(String displayAspectRatio) {
		this.displayAspectRatio = displayAspectRatio;
	}
	
	/**
	 * Get the frame rate mode
	 * @return The frame rate mode
	 */
	public String getFrameRateMode() {
		return frameRateMode;
	}
	
	/**
	 * Set the frame rate mode
	 * @param frameRateMode The frame rate mode
	 */
	public void setFrameRateMode(String frameRateMode) {
		this.frameRateMode = frameRateMode;
	}
	
	/**
	 * Get the frame rate 
	 * @return The frame rate
	 */
	public String getFrameRate() {
		return frameRate;
	}
	
	/**
	 * Set the frame rate
	 * @param frameRate The frame rate
	 */
	public void setFrameRate(String frameRate) {
		this.frameRate = frameRate;
	}
	
	
}
