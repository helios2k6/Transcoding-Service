package com.nlogneg.transcodingService.demultiplex;

import java.nio.file.Path;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;

/**
 * Represents a demultiplex job that does nothing. Specifically used for files
 * that are passed in that aren't media files
 * @author anjohnson
 *
 */
public final class NoOpDemultiplexJob extends DemultiplexJob{

	public NoOpDemultiplexJob(Path mediaFile, MediaInfo mediaInfo) {
		super(mediaFile, mediaInfo);
	}
	
}
