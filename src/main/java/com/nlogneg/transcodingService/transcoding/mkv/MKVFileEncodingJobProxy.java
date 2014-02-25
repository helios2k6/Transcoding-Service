package com.nlogneg.transcodingService.transcoding.mkv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.request.Request;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A proxy object for MKV File Encoding Jobs
 * @author Andrew
 *
 */
public class MKVFileEncodingJobProxy extends Proxy{
	public static final String PROXY_NAME = "MKVFileEncodingJobProxy";
	
	private final ConcurrentMap<Request, MKVFileEncodingJob> encodingJobs = new ConcurrentHashMap<Request, MKVFileEncodingJob>();
	
	/**
	 * Constructs a MKV File Encoding Job Proxy
	 */
	public MKVFileEncodingJobProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Add a request and encoding job mapping
	 * @param request The request
	 * @param job The encoding job
	 */
	public void put(Request request, MKVFileEncodingJob job){
		encodingJobs.putIfAbsent(request, job);
	}
	
	/**
	 * Get the encoding job from the request
	 * @param request The request
	 * @return The encoding job
	 */
	public Optional<MKVFileEncodingJob> get(Request request){
		return Optional.make(encodingJobs.get(request));
	}
	
	/**
	 * Removes a mapping
	 * @param request The key to remove the mapping
	 */
	public void removeMapping(Request request){
		encodingJobs.remove(request);
	}
}
