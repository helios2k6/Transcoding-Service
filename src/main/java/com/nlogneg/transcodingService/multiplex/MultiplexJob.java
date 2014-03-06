package com.nlogneg.transcodingService.multiplex;

import java.nio.file.Path;

import com.nlogneg.transcodingService.encoding.AudioTrackOption;

/**
 * Represents the multiplexing job
 * @author anjohnson
 *
 */
public final class MultiplexJob{
	private final Path encodedVideoFile; 
	private final AudioTrackOption audioTrackOption;
	private final Path destinationFile;
	/**
	 * @param encodedVideoFile
	 * @param audioTrackOption
	 * @param destinationFile
	 */
	public MultiplexJob(Path encodedVideoFile,
			AudioTrackOption audioTrackOption, Path destinationFile) {
		this.encodedVideoFile = encodedVideoFile;
		this.audioTrackOption = audioTrackOption;
		this.destinationFile = destinationFile;
	}
	
	/**
	 * @return the encodedVideoFile
	 */
	public Path getEncodedVideoFile() {
		return encodedVideoFile;
	}
	/**
	 * @return the audioTrackOption
	 */
	public AudioTrackOption getAudioTrackOption() {
		return audioTrackOption;
	}
	/**
	 * @return the destinationFile
	 */
	public Path getDestinationFile() {
		return destinationFile;
	}
	
	
}
