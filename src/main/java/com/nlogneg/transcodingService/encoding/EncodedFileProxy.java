package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Holds the location of the temporary encoded video bit stream 
 * @author anjohnson
 *
 */
public class EncodedFileProxy extends Proxy{
	public static final String PROXY_NAME = "EncodedFileProxy";
	
	private final ConcurrentMap<EncodingJob, Path> encodedFileMap = new ConcurrentHashMap<>();
	
	public EncodedFileProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Put a mapping between an encoding job and a path
	 * @param job 
	 * @param path
	 */
	public void put(EncodingJob job, Path path){
		encodedFileMap.put(job, path);
	}
	
	/**
	 * Get the path to the encoded bit stream file for a given Encoding Job
	 * @param job
	 * @return
	 */
	public Optional<Path> get(EncodingJob job){
		return Optional.make(encodedFileMap.get(job));
	}
	
	/**
	 * Remove a mapping between an Encoding job and a Path
	 * @param job
	 * @return
	 */
	public Path remove(EncodingJob job){
		return encodedFileMap.remove(job);
	}
}
