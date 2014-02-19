package com.nlogneg.transcodingService.mediaInfo;

import java.util.HashMap;
import java.util.Map;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

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
	 * @return An optional representing the MediaInfo
	 */
	public Optional<MediaInfo> getMediaInfo(String fileName){
		return Optional.make(map.get(fileName));
	}
}
