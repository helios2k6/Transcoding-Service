package com.nlogneg.transcodingService.mediaInfo;

/**
 * Represents the General type track
 * @author anjohnson
 *
 */
public final class GeneralTrack extends Track{

	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -1656903100542171365L;
	
	private String completeName;

	/**
	 * Gets the complete name of the file
	 * @return The complete name of the file
	 */
	public String getCompleteName() {
		return completeName;
	}

	/**
	 * Sets the complete name of the file
	 * @param completeName The complete name of the file
	 */
	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}
}
