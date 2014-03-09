package com.nlogneg.transcodingService.demultiplex;

import java.nio.file.Path;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;

/**
 * Represents what needs to be demultiplexed and extracted from a file
 * @author anjohnson
 *
 */
public abstract class DemultiplexJob{
	private final Path mediaFile;
	private final MediaInfo mediaInfo;

	/**
	 * @param mediaFile The media file to demultiplex
	 */
	public DemultiplexJob(Path mediaFile, MediaInfo mediaInfo){
		this.mediaFile = mediaFile;
		this.mediaInfo = mediaInfo;
	}

	/**
	 * @return the mediaFile
	 */
	public Path getMediaFile() {
		return mediaFile;
	}

	/**
	 * @return the mediaInfo
	 */
	public MediaInfo getMediaInfo() {
		return mediaInfo;
	}
}
