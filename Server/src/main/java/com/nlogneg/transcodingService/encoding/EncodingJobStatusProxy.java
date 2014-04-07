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
 * 
 * @author Andrew
 * 
 */
public final class EncodingJobStatusProxy extends Proxy
{
	public static final String PROXY_NAME = "EncodingJobStatusProxy";

	private final Map<EncodingJob, JobStatus> videoStatus = new HashMap<>();
	private final Map<EncodingJob, JobStatus> audioStatus = new HashMap<>();
	private final Lock lock = new ReentrantLock();

	public EncodingJobStatusProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * Add a job to the proxy
	 * 
	 * @param job
	 */
	public void addJob(final EncodingJob job)
	{
		this.lock.lock();
		this.videoStatus.put(job, JobStatus.Pending);
		this.audioStatus.put(job, JobStatus.Pending);
		this.lock.unlock();
	}

	/**
	 * Set the status of a video encode
	 * 
	 * @param job
	 * @param status
	 */
	public void setVideoStatus(final EncodingJob job, final JobStatus status)
	{
		this.lock.lock();
		this.videoStatus.put(job, status);
		this.lock.unlock();
	}

	/**
	 * Set the status of an audio encode
	 * 
	 * @param job
	 * @param status
	 */
	public void setAudioStatus(final EncodingJob job, final JobStatus status)
	{
		this.lock.lock();
		this.audioStatus.put(job, status);
		this.lock.unlock();
	}

	/**
	 * Get the status of a particular job
	 * 
	 * @param job
	 * @return
	 */
	public StatusTuple getStatus(final EncodingJob job)
	{
		this.lock.lock();
		final StatusTuple tuple = new StatusTuple(
				this.videoStatus.get(job),
				this.audioStatus.get(job));
		this.lock.unlock();
		return tuple;
	}

	/**
	 * Remove a job
	 * 
	 * @param job
	 */
	public void removeJob(final EncodingJob job)
	{
		this.lock.lock();
		this.videoStatus.remove(job);
		this.audioStatus.remove(job);
		this.lock.unlock();
	}
}
