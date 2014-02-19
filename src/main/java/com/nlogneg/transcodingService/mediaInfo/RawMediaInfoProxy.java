package com.nlogneg.transcodingService.mediaInfo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Stores raw media info string data
 * @author anjohnson
 *
 */
public class RawMediaInfoProxy extends Proxy{
	public static final String PROXY_NAME = "RawMediaInfoProxy";
	
	private final ConcurrentMap<String, String> rawMediaInfoMap;
	
	/**
	 * Constructs a new RawMediaInfoProxy
	 */
	public RawMediaInfoProxy(){
		super(PROXY_NAME);
		rawMediaInfoMap = new ConcurrentHashMap<String, String>();
	}
	
	/**
	 * Put a mapping into the proxy
	 * @param fileName The file name
	 * @param rawMediaInfo The raw media info data
	 */
	public void put(String fileName, String rawMediaInfo){
		rawMediaInfoMap.putIfAbsent(fileName, rawMediaInfo);
	}
	
	/**
	 * Get the media info for a specified file 
	 * @param fileName The file name
	 * @return An optional representing the raw MediaInfo
	 */
	public Optional<String> get(String fileName){
		return Optional.make(rawMediaInfoMap.get(fileName));
	}
}
