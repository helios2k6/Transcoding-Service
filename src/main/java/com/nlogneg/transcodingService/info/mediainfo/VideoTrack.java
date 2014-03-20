package com.nlogneg.transcodingService.info.mediainfo;

public final class VideoTrack extends MediaTrack{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8828400707334082736L;

	private final String width;
	private final String height;
	private final String displayAspectRatio;
	private final String frameRateMode;
	private final String frameRate;
	
	/**
	 * Constructs a new video track
	 * @param format The format
	 * @param codecID The codec ID
	 * @param id The track ID
	 * @param width The width of the video
	 * @param height The height of the video
	 * @param displayAspectRatio The display aspect ratio
	 * @param frameRateMode The frame rate mode
	 * @param frameRate The frame rate
	 */
	public VideoTrack(
			String format, 
			String codecID,
			int id,
			String width,
			String height, 
			String displayAspectRatio,
			String frameRateMode,
			String frameRate){
		super(format, codecID, id);
		this.width = width;
		this.height = height;
		this.displayAspectRatio = displayAspectRatio;
		this.frameRateMode = frameRateMode;
		this.frameRate = frameRate;
	}

	/**
	 * Get the width
	 * @return The width
	 */
	public String getWidth() {
		return width;
	}
	
	/**
	 * Get the height
	 * @return The height
	 */
	public String getHeight() {
		return height;
	}
	
	/**
	 * Get the display aspect ratio
	 * @return The display aspect ratio
	 */
	public String getDisplayAspectRatio() {
		return displayAspectRatio;
	}
	
	/**
	 * Get the frame rate mode
	 * @return The frame rate mode
	 */
	public String getFrameRateMode() {
		return frameRateMode;
	}
	
	/**
	 * Get the frame rate 
	 * @return The frame rate
	 */
	public String getFrameRate() {
		return frameRate;
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
	
	public void accept(TrackVisitor visitor){
		visitor.visit(this);
	}
}
