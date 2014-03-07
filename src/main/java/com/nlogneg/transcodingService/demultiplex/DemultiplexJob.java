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
}
