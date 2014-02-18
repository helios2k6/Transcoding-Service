package com.nlogneg.transcodingService.mediaInfo;

/**
 * Represents a subtitle track
 * @author anjohnson
 *
 */
public class TextTrack extends MediaTrack{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4632359334810139562L;
	
	private final String language;

	/**
	 * Construct a TextTrack
	 * @param format The format
	 * @param codecID The codec ID
	 * @param language The language of the track
	 */
	public TextTrack(String format, String codecID, String language) {
		super(format, codecID);
		this.language = language;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
}
