package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.util.Set;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the subtitle track encoding options
 * @author anjohnson
 *
 */
public final class SubtitleTrackOption{
	private final Optional<Path> textTrackFilePath;
	private final Set<EncodingAction> encodingActions;
	/**
	 * @param textTrackFilePath
	 * @param encodingActions
	 */
	public SubtitleTrackOption(Optional<Path> textTrackFilePath,
			Set<EncodingAction> encodingActions) {
		this.textTrackFilePath = textTrackFilePath;
		this.encodingActions = encodingActions;
	}
	/**
	 * @return the textTrackFilePath
	 */
	public Optional<Path> getTextTrackFilePath() {
		return textTrackFilePath;
	}
	/**
	 * @return the encodingActions
	 */
	public Set<EncodingAction> getEncodingActions() {
		return encodingActions;
	}
}
