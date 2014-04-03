package com.nlogneg.transcodingService;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Contains all of the MediaFileRequests
 * 
 * @author anjohnson
 * 
 */
public final class MediaFileRequestProxy extends Proxy
{
	public static final String PROXY_NAME = "MediaFileRequestProxy";

	private final ConcurrentMap<Path, MediaFileRequest> requestMap = new ConcurrentHashMap<Path, MediaFileRequest>();

	public MediaFileRequestProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * Put a mapping in the proxy
	 * 
	 * @param path
	 * @param request
	 */
	public void put(final Path path, final MediaFileRequest request)
	{
		this.requestMap.putIfAbsent(path, request);
	}

	/**
	 * Get a MediaFileRequest
	 * 
	 * @param path
	 * @return
	 */
	public Optional<MediaFileRequest> get(final Path path)
	{
		return Optional.make(this.requestMap.get(path));
	}

	/**
	 * Remove a media file request
	 * 
	 * @param path
	 * @return
	 */
	public Optional<MediaFileRequest> remove(final Path path)
	{
		return Optional.make(this.requestMap.remove(path));
	}

}
