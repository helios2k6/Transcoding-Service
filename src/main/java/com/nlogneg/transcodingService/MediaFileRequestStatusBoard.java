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
 * @author Andrew
 *
 */
public final class MediaFileRequestStatusBoard{
	/**
	 * The row of statuses for a particular MediaFileRequest
	 * @author Andrew
	 *
	 */
	private final class StatusRowEntry{
		public volatile JobStatus DemultiplexJobStatus = JobStatus.Pending;
		public volatile JobStatus EncodingJobStatus = JobStatus.Pending;
		public volatile JobStatus MultiplexJobStatus = JobStatus.Pending;
	}
	
	private final ConcurrentMap<MediaFileRequest, StatusRowEntry> statusBoard = new ConcurrentHashMap<>();
	private final ConcurrentMap<DemultiplexJob, MediaFileRequest> demultiplexJobToRequestMap = new ConcurrentHashMap<>();
	private final ConcurrentMap<EncodingJob, MediaFileRequest> encodingJobToRequestMap = new ConcurrentHashMap<>();
	private final ConcurrentMap<MultiplexJob, MediaFileRequest> multiplexJobToRequestMap = new ConcurrentHashMap<>();
	
	/**
	 * Add a MediaFileRequest with initial statuses
	 * @param request
	 */
	public void addMediaFileRequest(MediaFileRequest request){
		StatusRowEntry rowEntry = new StatusRowEntry();
		
		statusBoard.put(request, rowEntry);
		demultiplexJobToRequestMap.put(request.getDemultiplexJob(), request);
		encodingJobToRequestMap.put(request.getEncodingJob(), request);
		multiplexJobToRequestMap.put(request.getMultiplexJob(), request);
	}
	
	/**
	 * Get the status row for a particular job
	 * @param job
	 * @param map
	 * @return
	 */
	private <T> Optional<StatusRowEntry> tryGetStatusRowEntry(T job, Map<T, MediaFileRequest> map){
		MediaFileRequest request = map.get(job);
		if(request == null){
			return Optional.none();
		}
		
		StatusRowEntry row = statusBoard.get(request);
		return Optional.make(row);
	}
	
	/**
	 * Update the status of the job
	 * @param job
	 * @param status
	 */
	public void updateJobStatus(DemultiplexJob job, JobStatus status){
		Optional<StatusRowEntry> rowEntry = tryGetStatusRowEntry(job, demultiplexJobToRequestMap);
		if(rowEntry.isNone()){
			return;
		}
		
		rowEntry.getValue().DemultiplexJobStatus = status;
	}

	/**
	 * Update the status of the job
	 * @param job
	 * @param status
	 */
	public void updateJobStatus(EncodingJob job, JobStatus status){
		Optional<StatusRowEntry> rowEntry = tryGetStatusRowEntry(job, encodingJobToRequestMap);
		if(rowEntry.isNone()){
			return;
		}
		
		rowEntry.getValue().EncodingJobStatus = status;
	}
	
	/**
	 * Update the status of the job
	 * @param job
	 * @param status
	 */
	public void updateJobStatus(MultiplexJob job, JobStatus status){
		Optional<StatusRowEntry> rowEntry = tryGetStatusRowEntry(job, multiplexJobToRequestMap);
		if(rowEntry.isNone()){
			return;
		}
		
		rowEntry.getValue().MultiplexJobStatus = status;
	}
}
