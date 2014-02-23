package com.nlogneg.transcodingService.transcoding;

import com.nlogneg.transcodingService.requests.Request;

/**
 * Represents an encoding job
 * @author Andrew
 *
 */
public abstract class EncodingJob{
	private final long id;
	private final String file;
	private final Request request;
	/**
	 * @param id
	 * @param file
	 * @param request
	 */
	public EncodingJob(long id, String file, Request request) {
		this.id = id;
		this.file = file;
		this.request = request;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}
	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
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
		if (file == null) {
			if (other.file != null) {
				return false;
			}
		} else if (!file.equals(other.file)) {
			return false;
		}
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
	
	
}
