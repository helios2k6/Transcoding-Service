package com.nlogneg.transcodingService.demultiplex;

import java.nio.file.Path;

/**
 * Represents what needs to be demultiplexed and extracted from a file
 * @author anjohnson
 *
 */
public abstract class DemultiplexJob{
	private final Path mediaFile;

	/**
	 * @param mediaFile The media file to demultiplex
	 */
	public DemultiplexJob(Path mediaFile) {
		this.mediaFile = mediaFile;
	}

	/**
	 * @return the mediaFile
	 */
	public Path getMediaFile() {
		return mediaFile;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mediaFile == null) ? 0 : mediaFile.hashCode());
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
		DemultiplexJob other = (DemultiplexJob) obj;
		if (mediaFile == null) {
			if (other.mediaFile != null) {
				return false;
			}
		} else if (!mediaFile.equals(other.mediaFile)) {
			return false;
		}
		return true;
	}
}
