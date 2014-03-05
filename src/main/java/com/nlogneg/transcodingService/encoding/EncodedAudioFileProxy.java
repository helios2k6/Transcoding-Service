package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Holds the location of an encoded audio file
 * @author anjohnson
 *
 */
public final class EncodedAudioFileProxy extends Proxy{
	public static final String PROXY_NAME = "EncodedAudioFileProxy";
	
	private final ConcurrentMap<EncodingJob, Path> encodedFiles = new ConcurrentHashMap<>();
	
	public EncodedAudioFileProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Puts a mapping in the proxy
	 * @param job
	 * @param encodedAudioFile
	 */
	public void put(EncodingJob job, Path encodedAudioFile){
		encodedFiles.putIfAbsent(job, encodedAudioFile);
	}
	
	/**
	 * Gets a mapping
	 * @param job
	 * @return
	 */
	public Optional<Path> get(EncodingJob job){
		return Optional.make(encodedFiles.get(job));
	}
	
	/**
	 * Removes a mapping
	 * @param job
	 * @return
	 */
	public Optional<Path> remove(EncodingJob job){
		return Optional.make(encodedFiles.remove(job));
	}
}
