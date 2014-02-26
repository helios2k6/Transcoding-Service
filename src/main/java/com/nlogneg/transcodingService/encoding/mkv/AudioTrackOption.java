package com.nlogneg.transcodingService.encoding.mkv;

import java.util.Set;

import com.nlogneg.transcodingService.encoding.EncodingAction;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the audio track option for an MKV Encoding Job
 * @author anjohnson
 *
 */
public final class AudioTrackOption{
	private final Optional<AudioTrack> audioTrack;
	private final Set<EncodingAction> encodingAction;
	
	/**
	 * @param audioTrack
	 * @param encodingAction
	 */
	public AudioTrackOption(
			Optional<AudioTrack> audioTrack,
			Set<EncodingAction> encodingAction){
		this.audioTrack = audioTrack;
		this.encodingAction = encodingAction;
	}

	/**
	 * @return the audioTrack
	 */
	public Optional<AudioTrack> getAudioTrack() {
		return audioTrack;
	}

	/**
	 * @return the encodingAction
	 */
	public Set<EncodingAction> getEncodingAction() {
		return encodingAction;
	}
}
