package com.nlogneg.transcodingService.demultiplex.mkv;

import java.nio.file.Path;
import java.util.List;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.info.mediainfo.Track;
import com.nlogneg.transcodingService.info.mkv.Attachment;

/**
 * Represents demultiplexing an MKV file
 * @author anjohnson
 *
 */
public final class DemultiplexMKVJob extends DemultiplexJob{
	private final List<Attachment> attachments;
	private final List<Track> tracks;
	
	/**
	 * Constructs a demultiplexing job for an MKV file
	 * @param mediaFile The path to the media file
	 * @param attachments The attachments to demultiplex
	 * @param tracks The tracks to demultiplex
	 */
	public DemultiplexMKVJob(
			Path mediaFile,
			List<Attachment> attachments,
			List<Track> tracks){
		super(mediaFile);
		this.attachments = attachments;
		this.tracks = tracks;
	}

	/**
	 * @return the attachments
	 */
	public List<Attachment> getAttachments() {
		return attachments;
	}

	/**
	 * @return the tracks
	 */
	public List<Track> getTracks() {
		return tracks;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((attachments == null) ? 0 : attachments.hashCode());
		result = prime * result + ((tracks == null) ? 0 : tracks.hashCode());
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
		DemultiplexMKVJob other = (DemultiplexMKVJob) obj;
		if (attachments == null) {
			if (other.attachments != null) {
				return false;
			}
		} else if (!attachments.equals(other.attachments)) {
			return false;
		}
		if (tracks == null) {
			if (other.tracks != null) {
				return false;
			}
		} else if (!tracks.equals(other.tracks)) {
			return false;
		}
		return true;
	}
}
