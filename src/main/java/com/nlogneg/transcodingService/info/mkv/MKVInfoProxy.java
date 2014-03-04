package com.nlogneg.transcodingService.info.mkv;

import java.nio.file.Path;
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
	
	private final ConcurrentMap<Path, MKVInfo> mkvInfoMap = new ConcurrentHashMap<>();
	
	/**
	 * Constructs a MKVInfoProxy
	 */
	public MKVInfoProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Put a mapping between a file and an MKVInfo
	 * @param info The MKV Info
	 */
	public void put(MKVInfo info){
		mkvInfoMap.putIfAbsent(info.getFilePath(), info);
	}
	
	/**
	 * Get an MKVInfo from a file name
	 * @filePath The file path
	 * @return An optional representing the MKVInfo
	 */
	public Optional<MKVInfo> get(Path filePath){
		return Optional.make(mkvInfoMap.get(filePath));
	}
}
