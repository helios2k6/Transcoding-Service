package com.nlogneg.transcodingService.demultiplex;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.JobStatus;
import com.nlogneg.transcodingService.StatusTuple;

/**
 * Holds the status of a DemultiplexJob
 * 
 * @author anjohnson
 * 
 */
public class DemultiplexJobStatusProxy extends Proxy
{
	public static final String PROXY_NAME = "DemultiplexJobStatusProxy";

	private final Map<DemultiplexJob, JobStatus> trackStatus = new HashMap<>();
	private final Map<DemultiplexJob, JobStatus> attachmentStatus = new HashMap<>();
	private final Lock lock = new ReentrantLock();

	public DemultiplexJobStatusProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * Add a job to the proxy
	 * 
	 * @param job
	 */
	public void addJob(final DemultiplexJob job)
	{
		this.lock.lock();
		this.trackStatus.put(job, JobStatus.Pending);
		this.attachmentStatus.put(job, JobStatus.Pending);
		this.lock.unlock();
	}

	/**
	 * Set the status of the track extraction
	 * 
	 * @param job
	 * @param status
	 */
	public void setTrackStatus(final DemultiplexJob job, final JobStatus status)
	{
		this.lock.lock();
		this.trackStatus.put(job, status);
		this.lock.unlock();
	}

	/**
	 * Set the status of the attachment extraction
	 * 
	 * @param job
	 * @param status
	 */
	public void setAttachmentStatus(
			final DemultiplexJob job,
			final JobStatus status)
	{
		this.lock.lock();
		this.attachmentStatus.put(job, status);
		this.lock.unlock();
	}

	/**
	 * Get the status of the job
	 * 
	 * @param job
	 * @return
	 */
	public StatusTuple getStatus(final DemultiplexJob job)
	{
		this.lock.lock();
		final StatusTuple tuple = new StatusTuple(
				this.trackStatus.get(job),
				this.attachmentStatus.get(job));
		this.lock.unlock();

		return tuple;
	}

	/**
	 * Remove the job from the proxy
	 * 
	 * @param job
	 */
	public void removeJob(final DemultiplexJob job)
	{
		this.lock.lock();
		this.trackStatus.remove(job);
		this.attachmentStatus.remove(job);
		this.lock.unlock();
	}
}
