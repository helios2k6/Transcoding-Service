package com.nlogneg.transcodingService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.multiplex.MultiplexJob;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the status board for any particular MediaFileRequest
 * 
 * @author Andrew
 * 
 */
public final class MediaFileRequestStatusBoard
{
	private final ConcurrentMap<MediaFileRequest, MediaFileRequestStatus> statusBoard = new ConcurrentHashMap<>();
	private final ConcurrentMap<DemultiplexJob, MediaFileRequest> demultiplexJobToRequestMap = new ConcurrentHashMap<>();
	private final ConcurrentMap<EncodingJob, MediaFileRequest> encodingJobToRequestMap = new ConcurrentHashMap<>();
	private final ConcurrentMap<MultiplexJob, MediaFileRequest> multiplexJobToRequestMap = new ConcurrentHashMap<>();

	/**
	 * Add a MediaFileRequest with initial statuses
	 * 
	 * @param request
	 */
	public void addMediaFileRequest(final MediaFileRequest request)
	{
		final MediaFileRequestStatus rowEntry = new MediaFileRequestStatus();

		this.statusBoard.put(request, rowEntry);
		this.demultiplexJobToRequestMap.put(request.getDemultiplexJob(),
				request);
		this.encodingJobToRequestMap.put(request.getEncodingJob(), request);
		this.multiplexJobToRequestMap.put(request.getMultiplexJob(), request);
	}

	/**
	 * Get the status row for a particular job
	 * 
	 * @param job
	 * @param map
	 * @return
	 */
	private <T> Optional<MediaFileRequestStatus> tryGetStatusRowEntry(
			final T job, final Map<T, MediaFileRequest> map)
	{
		final MediaFileRequest request = map.get(job);
		if (request == null)
		{
			return Optional.none();
		}

		final MediaFileRequestStatus row = this.statusBoard.get(request);
		return Optional.make(row);
	}

	/**
	 * Update the status of the job
	 * 
	 * @param job
	 * @param status
	 */
	public void updateJobStatus(final DemultiplexJob job, final JobStatus status)
	{
		final Optional<MediaFileRequestStatus> rowEntry = this
				.tryGetStatusRowEntry(job, this.demultiplexJobToRequestMap);
		if (rowEntry.isNone())
		{
			return;
		}

		rowEntry.getValue().setDemultiplexJobStatus(status);
	}

	/**
	 * Update the status of the job
	 * 
	 * @param job
	 * @param status
	 */
	public void updateJobStatus(final EncodingJob job, final JobStatus status)
	{
		final Optional<MediaFileRequestStatus> rowEntry = this
				.tryGetStatusRowEntry(job, this.encodingJobToRequestMap);
		if (rowEntry.isNone())
		{
			return;
		}

		rowEntry.getValue().setEncodingJobStatus(status);
	}

	/**
	 * Update the status of the job
	 * 
	 * @param job
	 * @param status
	 */
	public void updateJobStatus(final MultiplexJob job, final JobStatus status)
	{
		final Optional<MediaFileRequestStatus> rowEntry = this
				.tryGetStatusRowEntry(job, this.multiplexJobToRequestMap);
		if (rowEntry.isNone())
		{
			return;
		}

		rowEntry.getValue().setMultiplexJobStatus(status);
	}
}
