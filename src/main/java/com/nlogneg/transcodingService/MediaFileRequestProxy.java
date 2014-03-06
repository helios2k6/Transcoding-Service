package com.nlogneg.transcodingService;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Contains all of the MediaFileRequests
 * @author anjohnson
 *
 */
public class MediaFileRequestProxy extends Proxy{
	public static final String PROXY_NAME = "MediaFileRequestProxy";
	
	private final ConcurrentMap<Path, MediaFileRequest> requestMap = new ConcurrentHashMap<Path, MediaFileRequest>();
	
	public MediaFileRequestProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Put a mapping in the proxy
	 * @param path
	 * @param request
	 */
	public void put(Path path, MediaFileRequest request){
		requestMap.putIfAbsent(path, request);
	}
	
	/**
	 * Get a MediaFileRequest
	 * @param path
	 * @return
	 */
	public Optional<MediaFileRequest> get(Path path){
		return Optional.make(requestMap.get(path));
	}
	
	/**
	 * Remove a media file request
	 * @param path
	 * @return
	 */
	public Optional<MediaFileRequest> remove(Path path){
		return Optional.make(requestMap.remove(path));
	}
}
