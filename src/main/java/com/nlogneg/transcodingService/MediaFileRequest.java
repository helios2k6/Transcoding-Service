package com.nlogneg.transcodingService;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.multiplex.MultiplexJob;

public final class MediaFileRequest{
	public enum JobStatus
	{
		Pending,
		InProgress,
		Complete,
		Failed;
	}
	
	private final DemultiplexJob demultiplexJob;
	private final EncodingJob encodingJob;
	private final MultiplexJob multiplexJob;
	
	private JobStatus demultiplexJobStatus = JobStatus.Pending;
	private JobStatus encodingJobStatus = JobStatus.Pending;
	private JobStatus muJobStatus = JobStatus.Pending;
	
	/**
	 * @param demultiplexJob
	 * @param encodingJob
	 * @param multiplexJob
	 */
	public MediaFileRequest(
			DemultiplexJob demultiplexJob,
			EncodingJob encodingJob, 
			MultiplexJob multiplexJob){
		this.demultiplexJob = demultiplexJob;
		this.encodingJob = encodingJob;
		this.multiplexJob = multiplexJob;
	}

	/**
	 * @return the demultiplexJobStatus
	 */
	public JobStatus getDemultiplexJobStatus() {
		return demultiplexJobStatus;
	}

	/**
	 * @param demultiplexJobStatus the demultiplexJobStatus to set
	 */
	public void setDemultiplexJobStatus(JobStatus demultiplexJobStatus) {
		this.demultiplexJobStatus = demultiplexJobStatus;
	}

	/**
	 * @return the encodingJobStatus
	 */
	public JobStatus getEncodingJobStatus() {
		return encodingJobStatus;
	}

	/**
	 * @param encodingJobStatus the encodingJobStatus to set
	 */
	public void setEncodingJobStatus(JobStatus encodingJobStatus) {
		this.encodingJobStatus = encodingJobStatus;
	}

	/**
	 * @return the muJobStatus
	 */
	public JobStatus getMuJobStatus() {
		return muJobStatus;
	}

	/**
	 * @param muJobStatus the muJobStatus to set
	 */
	public void setMuJobStatus(JobStatus muJobStatus) {
		this.muJobStatus = muJobStatus;
	}

	/**
	 * @return the demultiplexJob
	 */
	public DemultiplexJob getDemultiplexJob() {
		return demultiplexJob;
	}

	/**
	 * @return the encodingJob
	 */
	public EncodingJob getEncodingJob() {
		return encodingJob;
	}

	/**
	 * @return the multiplexJob
	 */
	public MultiplexJob getMultiplexJob() {
		return multiplexJob;
	}
	
	
}
