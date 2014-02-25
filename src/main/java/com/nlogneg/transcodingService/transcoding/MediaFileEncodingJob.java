package com.nlogneg.transcodingService.transcoding;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.request.incoming.Selector;

/**
 * Represents encoding a media file
 * @author Andrew
 *
 */
public abstract class MediaFileEncodingJob extends EncodingJob{
	private final MediaInfo mediaInfo;
	private final Selector selector;
	
	/**
	 * @param id
	 * @param file
	 * @param request
	 * @param mediaInfo
	 * @param selector
	 */
	public MediaFileEncodingJob(
			long id,
			String file, 
			Request request,
			MediaInfo mediaInfo, 
			Selector selector){
		super(id, file, request);
		this.mediaInfo = mediaInfo;
		this.selector = selector;
	}
	
	/**
	 * @return the mediaInfo
	 */
	public MediaInfo getMediaInfo() {
		return mediaInfo;
	}
	/**
	 * @return the selector
	 */
	public Selector getSelector() {
		return selector;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((mediaInfo == null) ? 0 : mediaInfo.hashCode());
		result = prime * result
				+ ((selector == null) ? 0 : selector.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MediaFileEncodingJob other = (MediaFileEncodingJob) obj;
		if (mediaInfo == null) {
			if (other.mediaInfo != null) {
				return false;
			}
		} else if (!mediaInfo.equals(other.mediaInfo)) {
			return false;
		}
		if (selector == null) {
			if (other.selector != null) {
				return false;
			}
		} else if (!selector.equals(other.selector)) {
			return false;
		}
		return true;
	}
}
