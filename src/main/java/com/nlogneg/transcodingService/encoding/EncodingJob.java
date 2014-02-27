package com.nlogneg.transcodingService.encoding;

import java.util.concurrent.atomic.AtomicLong;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.request.incoming.Request;

/**
 * Represents a transcoding job
 * @author anjohnson
 *
 */
public abstract class EncodingJob{
	private static final AtomicLong IDSeed = new AtomicLong(0);
	
	private final long id;
	private final Request request;
	private final MediaInfo mediaInfo;
	
	/**
	 * Constructs a new encoding job
	 * @param request The request
	 * @param mediaInfo The media info
	 */
	protected EncodingJob(Request request, MediaInfo mediaInfo){
		this.id = IDSeed.incrementAndGet();
		this.request = request;
		this.mediaInfo = mediaInfo;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * @return the mediaInfo
	 */
	public MediaInfo getMediaInfo() {
		return mediaInfo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((request == null) ? 0 : request.hashCode());
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
		EncodingJob other = (EncodingJob) obj;
		if (id != other.id) {
			return false;
		}
		if (request == null) {
			if (other.request != null) {
				return false;
			}
		} else if (!request.equals(other.request)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Accepts a visitor
	 * @param visitor
	 */
	public abstract void accept(EncodingJobVisitor visitor);
}
