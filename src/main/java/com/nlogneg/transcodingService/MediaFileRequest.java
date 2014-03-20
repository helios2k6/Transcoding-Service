package com.nlogneg.transcodingService;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.multiplex.MultiplexJob;

/**
 * Represents the master request to transcode a MediaFile
 * 
 * @author Andrew
 * 
 */
public final class MediaFileRequest
{
	private final DemultiplexJob demultiplexJob;
	private final EncodingJob encodingJob;
	private final MultiplexJob multiplexJob;

	/**
	 * @param demultiplexJob
	 * @param encodingJob
	 * @param multiplexJob
	 */
	public MediaFileRequest(final DemultiplexJob demultiplexJob,
			final EncodingJob encodingJob, final MultiplexJob multiplexJob)
	{
		this.demultiplexJob = demultiplexJob;
		this.encodingJob = encodingJob;
		this.multiplexJob = multiplexJob;
	}

	/**
	 * @return the demultiplexJob
	 */
	public DemultiplexJob getDemultiplexJob()
	{
		return this.demultiplexJob;
	}

	/**
	 * @return the encodingJob
	 */
	public EncodingJob getEncodingJob()
	{
		return this.encodingJob;
	}

	/**
	 * @return the multiplexJob
	 */
	public MultiplexJob getMultiplexJob()
	{
		return this.multiplexJob;
	}
}
