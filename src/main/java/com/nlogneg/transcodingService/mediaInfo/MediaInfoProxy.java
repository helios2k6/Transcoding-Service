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
	
	public void putMediaInfo(String fileName, MediaInfo mediaInfo){
		map.put(fileName,  mediaInfo);
	}
}
