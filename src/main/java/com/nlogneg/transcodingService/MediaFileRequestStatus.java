package com.nlogneg.transcodingService;


/**
 * Contains all of the job status of a particular set of Jobs
 * @author Andrew
 *
 */
public final class MediaFileRequestStatus{
	private JobStatus demultiplexJobStatus = JobStatus.Pending;
	private JobStatus encodingJobStatus = JobStatus.Pending;
	private JobStatus multiplexJobStatus = JobStatus.Pending;
	
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
	 * @return the multiplexJobStatus
	 */
	public JobStatus getMultiplexJobStatus() {
		return multiplexJobStatus;
	}
	/**
	 * @param multiplexJobStatus the multiplexJobStatus to set
	 */
	public void setMultiplexJobStatus(JobStatus multiplexJobStatus) {
		this.multiplexJobStatus = multiplexJobStatus;
	}
}
