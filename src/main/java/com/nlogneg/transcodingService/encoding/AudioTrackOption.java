package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the audio track option for an MKV Encoding Job
 * @author anjohnson
 *
 */
public final class AudioTrackOption{
	private final Optional<Path> audioTrackFilePath;
	private final EncodingAction encodingAction;
	
	/**
	 * @param audioTrackFilePath
	 * @param encodingAction
	 */
	public AudioTrackOption(Optional<Path> audioTrackFilePath,
			EncodingAction encodingAction) {
		this.audioTrackFilePath = audioTrackFilePath;
		this.encodingAction = encodingAction;
	}
	
	/**
	 * @return the audioTrackFilePath
	 */
	public Optional<Path> getAudioTrackFilePath() {
		return audioTrackFilePath;
	}
	/**
	 * @return the encodingAction
	 */
	public EncodingAction getEncodingAction() {
		return encodingAction;
	}
}
