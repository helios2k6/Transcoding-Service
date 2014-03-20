package com.nlogneg.transcodingService.multiplex;

import java.nio.file.Path;

import com.nlogneg.transcodingService.encoding.AudioTrackOption;

/**
 * Factory class that creates MultiplexJobs
 * @author Andrew
 *
 */
public final class MultiplexJobFactory {
	
	/**
	 * Creates a new MultiplexJOb
	 * @param encodedVideoFile
	 * @param audioTrack
	 * @param destinationFile
	 * @return
	 */
	public static MultiplexJob createMultiplexJob(
			Path encodedVideoFile, 
			AudioTrackOption audioTrack,
			Path destinationFile){
		return new MultiplexJob(encodedVideoFile, audioTrack, destinationFile);
	}
}
