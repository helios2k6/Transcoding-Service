package com.nlogneg.transcodingService.demultiplex;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.JobStatus;


/**
 * Holds the status of a DemultiplexJob
 * @author anjohnson
 *
 */
public class DemultiplexJobStatusProxy extends Proxy{
	public static final String PROXY_NAME = "DemultiplexJobStatusProxy";
	
	private final Map<DemultiplexJob, JobStatus> trackStatus = new HashMap<>();
	private final Map<DemultiplexJob, JobStatus> attachmentStatus = new HashMap<>();
	private final Lock lock = new ReentrantLock();

	public DemultiplexJobStatusProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Add a job to the proxy
	 * @param job
	 */
	public void addJob(DemultiplexJob job){
		lock.lock();
		trackStatus.put(job, JobStatus.Pending);
		attachmentStatus.put(job, JobStatus.Pending);
		lock.unlock();
	}
	
	/**
	 * Set the status of the track extraction
	 * @param job
	 * @param status
	 */
	public void setTrackStatus(DemultiplexJob job, JobStatus status){
		lock.lock();
		trackStatus.put(job, status);
		lock.unlock();
	}
	
	/**
	 * Set the status of the attachment extraction
	 * @param job
	 * @param status
	 */
	public void setAttachmentStatus(DemultiplexJob job, JobStatus status){
		lock.lock();
		attachmentStatus.put(job, status);
		lock.unlock();
	}
	
	/**
	 * Get the status of the job
	 * @param job
	 * @return
	 */
	public StatusTuple getStatus(DemultiplexJob job){
		lock.lock();
		StatusTuple tuple = new StatusTuple(trackStatus.get(job), attachmentStatus.get(job));
		lock.unlock();
		
		return tuple;
	}
	
	/**
	 * Remove the job from the proxy
	 * @param job
	 */
	public void removeJob(DemultiplexJob job){
		lock.lock();
		trackStatus.remove(job);
		attachmentStatus.remove(job);
		lock.unlock();
	}
}
