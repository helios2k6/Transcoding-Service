package com.nlogneg.transcodingService.encoding;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.JobStatus;
import com.nlogneg.transcodingService.StatusTuple;

/**
 * Holds the state of an EncodingJob
 * @author Andrew
 *
 */
public class EncodingJobStatusProxy extends Proxy{
	public static final String PROXY_NAME = "EncodingJobStatusProxy";
	
	private final Map<EncodingJob, JobStatus> videoStatus = new HashMap<>();
	private final Map<EncodingJob, JobStatus> audioStatus = new HashMap<>();
	private final Lock lock = new ReentrantLock();
	
	public EncodingJobStatusProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Add a job to the proxy
	 * @param job
	 */
	public void addJob(EncodingJob job){
		lock.lock();
		videoStatus.put(job, JobStatus.Pending);
		audioStatus.put(job, JobStatus.Pending);
		lock.unlock();
	}
	
	/**
	 * Set the status of a video encode
	 * @param job
	 * @param status
	 */
	public void setVideoStatus(EncodingJob job, JobStatus status){
		lock.lock();
		videoStatus.put(job, status);
		lock.unlock();
	}
	
	/**
	 * Set the status of an audio encode
	 * @param job
	 * @param status
	 */
	public void setAudioStatus(EncodingJob job, JobStatus status){
		lock.lock();
		audioStatus.put(job, status);
		lock.unlock();
	}
	
	/**
	 * Get the status of a particular job
	 * @param job
	 * @return
	 */
	public StatusTuple getStatus(EncodingJob job){
		lock.lock();
		StatusTuple tuple = new StatusTuple(videoStatus.get(job), audioStatus.get(job));
		lock.unlock();
		return tuple;
	}
	
	/**
	 * Remove a job
	 * @param job
	 */
	public void removeJob(EncodingJob job){
		lock.lock();
		videoStatus.remove(job);
		audioStatus.remove(job);
		lock.unlock();
	}
}
