package com.nlogneg.transcodingService.demultiplex;

import com.nlogneg.transcodingService.JobStatus;

/**
 * Represents the status of a Demultiplex Job
 * @author Andrew
 *
 */
public final class DemultiplexJobStatus{
	private JobStatus attachmentStatus = JobStatus.Pending;
	private JobStatus trackStatus = JobStatus.Pending;
	
	/**
	 * @return the attachmentStatus
	 */
	public JobStatus getAttachmentStatus() {
		return attachmentStatus;
	}
	/**
	 * @param attachmentStatus the attachmentStatus to set
	 */
	public void setAttachmentStatus(JobStatus attachmentStatus) {
		this.attachmentStatus = attachmentStatus;
	}
	/**
	 * @return the trackStatus
	 */
	public JobStatus getTrackStatus() {
		return trackStatus;
	}
	/**
	 * @param trackStatus the trackStatus to set
	 */
	public void setTrackStatus(JobStatus trackStatus) {
		this.trackStatus = trackStatus;
	}
}
