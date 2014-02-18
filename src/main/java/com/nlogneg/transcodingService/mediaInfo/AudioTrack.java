package com.nlogneg.transcodingService.mediaInfo;

/**
 * Represents an audio track in a mediainfo xml file
 * @author anjohnson
 *
 */
public class AudioTrack extends MediaTrack{

	/**
	 * 
	 */
	private static final long serialVersionUID = -327107175740055297L;

	private String channels;
	private String language;
	
	/**
	 * Get the number of channels
	 * @return The number of channels
	 */
	public String getChannels() {
		return channels;
	}
	
	/**
	 * Set the number of channels
	 * @param channels The number of channels
	 */
	public void setChannels(String channels) {
		this.channels = channels;
	}
	
	/**
	 * Get the language
	 * @return The language
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Set the language
	 * @param language The language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
