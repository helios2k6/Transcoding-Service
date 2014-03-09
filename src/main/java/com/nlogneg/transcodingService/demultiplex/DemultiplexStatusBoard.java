package com.nlogneg.transcodingService.demultiplex;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Tracks that status of a demultiplexing job
 * @author Andrew
 *
 */
public final class DemultiplexStatusBoard{
	private final ConcurrentMap<DemultiplexJob, DemultiplexJobStatus> statusBoard = new ConcurrentHashMap<>();
	
	/**
	 * Add a job to the status board
	 * @param job
	 */
	public void addJob(DemultiplexJob job){
		DemultiplexJobStatus initialStatus = new DemultiplexJobStatus();
		statusBoard.put(job,  initialStatus);
	}
	
	/**
	 * Get the status of a job
	 * @param job
	 * @return
	 */
	public Optional<DemultiplexJobStatus> getStatus(DemultiplexJob job){
		return Optional.make(statusBoard.get(job));
	}
	
	/**
	 * Remove a job from the status board
	 * @param job
	 * @return
	 */
	public Optional<DemultiplexJobStatus> removeJob(DemultiplexJob job){
		return Optional.make(statusBoard.remove(job));
	}
}
