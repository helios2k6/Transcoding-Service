package com.nlogneg.transcodingService.encoding.mkv;

import java.nio.file.Path;
import java.util.Set;

import com.nlogneg.transcodingService.encoding.EncodingAction;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the audio track option for an MKV Encoding Job
 * @author anjohnson
 *
 */
public final class AudioTrackOption{
	private final Optional<Path> audioTrackFilePath;
	private final Set<EncodingAction> encodingAction;
	
	/**
	 * @param audioTrackFilePath
	 * @param encodingAction
	 */
	public AudioTrackOption(Optional<Path> audioTrackFilePath,
			Set<EncodingAction> encodingAction) {
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
	public Set<EncodingAction> getEncodingAction() {
		return encodingAction;
	}
}
