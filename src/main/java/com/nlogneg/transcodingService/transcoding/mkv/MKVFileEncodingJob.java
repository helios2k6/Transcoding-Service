package com.nlogneg.transcodingService.transcoding.mkv;

import com.nlogneg.transcodingService.mediaInfo.MediaInfo;
import com.nlogneg.transcodingService.requests.Request;
import com.nlogneg.transcodingService.requests.Selector;
import com.nlogneg.transcodingService.transcoding.MediaFileEncodingJob;

/**
 * Represents an encoding job for an MKV file
 * @author Andrew
 *
 */
public final class MKVFileEncodingJob extends MediaFileEncodingJob{
	private final MKVInfo mkvInfo;

	/**
	 * @param id
	 * @param file
	 * @param request
	 * @param mediaInfo
	 * @param selector
	 * @param mkvInfo
	 */
	public MKVFileEncodingJob(
			long id,
			String file, 
			Request request,
			MediaInfo mediaInfo,
			Selector selector,
			MKVInfo mkvInfo){
		super(id, file, request, mediaInfo, selector);
		this.mkvInfo = mkvInfo;
	}

	/**
	 * @return the mkvInfo
	 */
	public MKVInfo getMkvInfo() {
		return mkvInfo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mkvInfo == null) ? 0 : mkvInfo.hashCode());
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
		MKVFileEncodingJob other = (MKVFileEncodingJob) obj;
		if (mkvInfo == null) {
			if (other.mkvInfo != null) {
				return false;
			}
		} else if (!mkvInfo.equals(other.mkvInfo)) {
			return false;
		}
		return true;
	}
}
