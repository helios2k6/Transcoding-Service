package com.nlogneg.transcodingService.media;

import java.io.Serializable;

/**
 * Represents all of the media container formats
 * @author anjohnson
 *
 */
public enum MediaContainerFormat implements Serializable{
	Matroska,
	MPEG_4,
	AVI,
	WMV,
	Unknown;
	
	/**
	 * Parses a string into a Media Container Format
	 * @param string
	 * @return
	 */
	public static MediaContainerFormat Parse(String string){
		if(string.equalsIgnoreCase("Matroska")) return MediaContainerFormat.Matroska;
		if(string.equalsIgnoreCase("MPEG-4") || string.equals("MPEG4") || string.equals("MPEG 4")) return MediaContainerFormat.MPEG_4;
		if(string.equalsIgnoreCase("AVI")) return MediaContainerFormat.AVI;
		if(string.equalsIgnoreCase("WMV")) return MediaContainerFormat.WMV;
		
		return MediaContainerFormat.Unknown;
	}
}
