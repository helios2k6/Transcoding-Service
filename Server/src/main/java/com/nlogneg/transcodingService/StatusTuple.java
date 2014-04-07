package com.nlogneg.transcodingService;

/**
 * Represents a tuple of JobStatus
 * 
 * @author Andrew
 * 
 */
public class StatusTuple
{
	private final JobStatus statusOne;
	private final JobStatus statusTwo;

	public StatusTuple(
			final JobStatus trackStatus,
			final JobStatus attachmentStatus)
	{
		this.statusOne = trackStatus;
		this.statusTwo = attachmentStatus;
	}

	/**
	 * @return the statusOne
	 */
	public JobStatus getStatusOne()
	{
		return this.statusOne;
	}

	/**
	 * @return the statusTwo
	 */
	public JobStatus getStatusTwo()
	{
		return this.statusTwo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.statusTwo == null) ? 0 : this.statusTwo.hashCode());
		result = (prime * result) + ((this.statusOne == null) ? 0 : this.statusOne.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final StatusTuple other = (StatusTuple) obj;
		if (this.statusTwo != other.statusTwo)
		{
			return false;
		}
		if (this.statusOne != other.statusOne)
		{
			return false;
		}
		return true;
	}

}
