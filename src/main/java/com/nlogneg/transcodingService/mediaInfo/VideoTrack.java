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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((displayAspectRatio == null) ? 0 : displayAspectRatio
						.hashCode());
		result = prime * result
				+ ((frameRate == null) ? 0 : frameRate.hashCode());
		result = prime * result
				+ ((frameRateMode == null) ? 0 : frameRateMode.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		VideoTrack other = (VideoTrack) obj;
		if (displayAspectRatio == null) {
			if (other.displayAspectRatio != null) {
				return false;
			}
		} else if (!displayAspectRatio.equals(other.displayAspectRatio)) {
			return false;
		}
		if (frameRate == null) {
			if (other.frameRate != null) {
				return false;
			}
		} else if (!frameRate.equals(other.frameRate)) {
			return false;
		}
		if (frameRateMode == null) {
			if (other.frameRateMode != null) {
				return false;
			}
		} else if (!frameRateMode.equals(other.frameRateMode)) {
			return false;
		}
		if (height == null) {
			if (other.height != null) {
				return false;
			}
		} else if (!height.equals(other.height)) {
			return false;
		}
		if (width == null) {
			if (other.width != null) {
				return false;
			}
		} else if (!width.equals(other.width)) {
			return false;
		}
		return true;
	}
}
