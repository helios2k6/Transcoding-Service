package com.nlogneg.transcodingService.encoding;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Holds encoding jobs
 * @author Andrew
 *
 */
public final class EncodingJobProxy extends Proxy{
	public static final String PROXY_NAME = "EncodingJobProxy";
	
	private final ConcurrentMap<Long, EncodingJob> jobMap = new ConcurrentHashMap<>();
	
	public EncodingJobProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Put an encoding job in the map
	 * @param job
	 */
	public void put(EncodingJob job){
		jobMap.putIfAbsent(Long.valueOf(job.getId()), job);
	}
	
	/**
	 * Get an encoding job
	 * @param id
	 * @return
	 */
	public Optional<EncodingJob> get(long id){
		return Optional.make(jobMap.get(id));
	}
	
	/**
	 * Remove an encoding job
	 * @param id
	 */
	public void remove(long id){
		jobMap.remove(id);
	}
}
