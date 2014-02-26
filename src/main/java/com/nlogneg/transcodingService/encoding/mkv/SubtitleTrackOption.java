package com.nlogneg.transcodingService.encoding.mkv;

import java.util.Set;

import com.nlogneg.transcodingService.encoding.EncodingAction;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the subtitle track encoding options
 * @author anjohnson
 *
 */
public final class SubtitleTrackOption{
	private final Optional<TextTrack> textTrack;
	private final Set<EncodingAction> encodingActions;
	
	/**
	 * @param textTrack
	 * @param encodingActions
	 */
	public SubtitleTrackOption(Optional<TextTrack> textTrack,
			Set<EncodingAction> encodingActions) {
		this.textTrack = textTrack;
		this.encodingActions = encodingActions;
	}
	/**
	 * @return the textTrack
	 */
	public Optional<TextTrack> getTextTrack() {
		return textTrack;
	}
	/**
	 * @return the encodingActions
	 */
	public Set<EncodingAction> getEncodingActions() {
		return encodingActions;
	}
}
