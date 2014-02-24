package com.nlogneg.transcodingService.transcoding.mkv;

import java.util.List;

/**
 * Holds metadata about an MKV File
 * @author anjohnson
 *
 */
public final class MKVInfo{
	private final List<Attachment> attachments;

	/**
	 * Creates an MKV Info object
	 * @param attachments
	 */
	public MKVInfo(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the attachments
	 */
	public List<Attachment> getAttachments() {
		return attachments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attachments == null) ? 0 : attachments.hashCode());
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
		MKVInfo other = (MKVInfo) obj;
		if (attachments == null) {
			if (other.attachments != null) {
				return false;
			}
		} else if (!attachments.equals(other.attachments)) {
			return false;
		}
		return true;
	}
	
	
}
