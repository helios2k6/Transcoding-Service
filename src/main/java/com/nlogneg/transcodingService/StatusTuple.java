package com.nlogneg.transcodingService;

/**
 * Represents a tuple of JobStatus
 * @author Andrew
 *
 */
public class StatusTuple
{
	private final JobStatus statusOne;
	private final JobStatus statusTwo;
	
	public StatusTuple(JobStatus trackStatus, JobStatus attachmentStatus) {
		this.statusOne = trackStatus;
		this.statusTwo = attachmentStatus;
	}
	/**
	 * @return the statusOne
	 */
	public JobStatus getStatusOne() {
		return statusOne;
	}
	/**
	 * @return the statusTwo
	 */
	public JobStatus getStatusTwo() {
		return statusTwo;
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
				+ ((statusTwo == null) ? 0 : statusTwo.hashCode());
		result = prime * result
				+ ((statusOne == null) ? 0 : statusOne.hashCode());
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
		if (statusTwo != other.statusTwo) {
			return false;
		}
		if (statusOne != other.statusOne) {
			return false;
		}
		return true;
	}
	
}
