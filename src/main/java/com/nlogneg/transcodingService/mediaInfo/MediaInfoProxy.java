package com.nlogneg.transcodingService.mediaInfo;

import java.util.HashMap;
import java.util.Map;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Holds all of the MediaInfo objects
 * @author anjohnson
 *
 */
public class MediaInfoProxy extends Proxy{
	public static final String PROXY_NAME = "MediaInfoProxy";
	
	private final Map<String, MediaInfo> map;
	
	public MediaInfoProxy(){
		super(PROXY_NAME);
		
		map = new HashMap<String, MediaInfo>();
	}
	
	/**
	 * Puts a new mapping into the proxy
	 * @param fileName The file name
	 * @param mediaInfo The media info
	 */
	public void putMediaInfo(String fileName, MediaInfo mediaInfo){
		map.put(fileName,  mediaInfo);
	}
	
	/**
	 * Gets the media info for the given string
	 * @param fileName The file name
	 * @return The MediaInfo for the given file
	 */
	public MediaInfo getMediaInfo(String fileName){
		return map.get(fileName);
	}
}
