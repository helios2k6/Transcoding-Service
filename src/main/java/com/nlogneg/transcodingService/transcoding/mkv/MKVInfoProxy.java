package com.nlogneg.transcodingService.transcoding.mkv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Holds the MKVInfo objects
 * @author Andrew
 *
 */
public class MKVInfoProxy extends Proxy{
	public static final String PROXY_NAME = "MKVInfoProxy";
	
	private final ConcurrentMap<String, MKVInfo> mkvInfoMap = new ConcurrentHashMap<>();
	
	/**
	 * Constructs a MKVInfoProxy
	 */
	public MKVInfoProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Put a mapping between a file and an MKVInfo
	 * @param fileName The file name
	 * @param info The MKV Info
	 */
	public void put(String fileName, MKVInfo info){
		mkvInfoMap.putIfAbsent(fileName, info);
	}
	
	/**
	 * Get an MKVInfo from a file name
	 * @param fileName The file name
	 * @return An optional representing the MKVInfo
	 */
	public Optional<MKVInfo> get(String fileName){
		return Optional.make(mkvInfoMap.get(fileName));
	}
}
