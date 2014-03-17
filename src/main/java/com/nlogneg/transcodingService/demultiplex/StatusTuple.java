package com.nlogneg.transcodingService.demultiplex;

import com.nlogneg.transcodingService.JobStatus;

public class StatusTuple
{
	private final JobStatus trackStatus;
	private final JobStatus attachmentStatus;
	
	/**
	 * @param trackStatus
	 * @param attachmentStatus
	 */
	public StatusTuple(JobStatus trackStatus, JobStatus attachmentStatus) {
		this.trackStatus = trackStatus;
		this.attachmentStatus = attachmentStatus;
	}
	/**
	 * @return the trackStatus
	 */
	public JobStatus getTrackStatus() {
		return trackStatus;
	}
	/**
	 * @return the attachmentStatus
	 */
	public JobStatus getAttachmentStatus() {
		return attachmentStatus;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((attachmentStatus == null) ? 0 : attachmentStatus.hashCode());
		result = prime * result
				+ ((trackStatus == null) ? 0 : trackStatus.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		StatusTuple other = (StatusTuple) obj;
		if (attachmentStatus != other.attachmentStatus) {
			return false;
		}
		if (trackStatus != other.trackStatus) {
			return false;
		}
		return true;
	}
	
}
