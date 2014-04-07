package com.nlogneg.transcodingService;

/**
 * Contains all of the job status of a particular set of Jobs
 * 
 * @author Andrew
 * 
 */
public final class MediaFileRequestStatus
{
	private JobStatus demultiplexJobStatus = JobStatus.Pending;
	private JobStatus encodingJobStatus = JobStatus.Pending;
	private JobStatus multiplexJobStatus = JobStatus.Pending;

	/**
	 * @return the demultiplexJobStatus
	 */
	public JobStatus getDemultiplexJobStatus()
	{
		return this.demultiplexJobStatus;
	}

	/**
	 * @param demultiplexJobStatus
	 *            the demultiplexJobStatus to set
	 */
	public void setDemultiplexJobStatus(final JobStatus demultiplexJobStatus)
	{
		this.demultiplexJobStatus = demultiplexJobStatus;
	}

	/**
	 * @return the encodingJobStatus
	 */
	public JobStatus getEncodingJobStatus()
	{
		return this.encodingJobStatus;
	}

	/**
	 * @param encodingJobStatus
	 *            the encodingJobStatus to set
	 */
	public void setEncodingJobStatus(final JobStatus encodingJobStatus)
	{
		this.encodingJobStatus = encodingJobStatus;
	}

	/**
	 * @return the multiplexJobStatus
	 */
	public JobStatus getMultiplexJobStatus()
	{
		return this.multiplexJobStatus;
	}

	/**
	 * @param multiplexJobStatus
	 *            the multiplexJobStatus to set
	 */
	public void setMultiplexJobStatus(final JobStatus multiplexJobStatus)
	{
		this.multiplexJobStatus = multiplexJobStatus;
	}
}
