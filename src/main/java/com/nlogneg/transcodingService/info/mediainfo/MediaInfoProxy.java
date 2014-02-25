package com.nlogneg.transcodingService.info.mediainfo;

import java.nio.file.Path;
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
	 * @param filePath The file path
	 * @param mediaInfo The media info
	 */
	public void putMediaInfo(Path filePath, MediaInfo mediaInfo){
		map.put(filePath.toAbsolutePath().toString(),  mediaInfo);
	}
	
	/**
	 * Gets the media info for the given string
	 * @param filePath The file path
	 * @return An optional representing the MediaInfo
	 */
	public Optional<MediaInfo> getMediaInfo(Path filePath){
		return Optional.make(map.get(filePath.toAbsolutePath().toString()));
	}
}
