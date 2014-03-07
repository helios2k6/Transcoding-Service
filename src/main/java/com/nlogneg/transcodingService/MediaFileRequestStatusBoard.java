package com.nlogneg.transcodingService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.multiplex.MultiplexJob;

/**
 * Represents the status board for any particular MediaFileRequest
 * @author Andrew
 *
 */
public final class MediaFileRequestStatusBoard{
	/**
	 * The row of statuses for a particular MediaFileRequest
	 * @author Andrew
	 *
	 */
	private final class StatusRowEntry{
		public volatile JobStatus DemultiplexJobStatus = JobStatus.Pending;
		public volatile JobStatus EncodingJobStatus = JobStatus.Pending;
		public volatile JobStatus MultiplexJobStatus = JobStatus.Pending;
	}
	
	private final Map<MediaFileRequest, StatusRowEntry> statusBoard = new HashMap<>();
	private final Lock lock = new ReentrantLock();
	
	/**
	 * Add a MediaFileRequest with initial statuses
	 * @param request
	 */
	public void addMediaFileRequest(MediaFileRequest request){
		lock.lock();
		StatusRowEntry rowEntry = new StatusRowEntry();
		statusBoard.put(request, rowEntry);
		lock.unlock();
	}
	
	public void updateJobStatus(DemultiplexJob job, JobStatus status){
		lock.lock();
		lock.unlock();
	}
	
	public void updateJobStatus(EncodingJob job, JobStatus status){
		lock.lock();
		lock.unlock();
	}
	
	public void updateJobStatus(MultiplexJob job, JobStatus status){
		lock.lock();
		lock.unlock();
	}
}
