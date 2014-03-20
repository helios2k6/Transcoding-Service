package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;

import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the audio track option for an MKV Encoding Job
 * 
 * @author anjohnson
 * 
 */
public final class AudioTrackOption
{
	private final Optional<Path> audioTrackFilePath;
	private final EncodingAction encodingAction;
	private final Optional<AudioTrack> audioTrack;

	/**
	 * @param audioTrackFilePath
	 * @param encodingAction
	 */
	public AudioTrackOption(final Optional<Path> audioTrackFilePath,
			final EncodingAction encodingAction,
			final Optional<AudioTrack> audioTrack)
	{
		this.audioTrackFilePath = audioTrackFilePath;
		this.encodingAction = encodingAction;
		this.audioTrack = audioTrack;
	}

	/**
	 * @return the audioTrackFilePath
	 */
	public Optional<Path> getAudioTrackFilePath()
	{
		return this.audioTrackFilePath;
	}

	/**
	 * @return the encodingAction
	 */
	public EncodingAction getEncodingAction()
	{
		return this.encodingAction;
	}

	/**
	 * Get the audio track
	 * 
	 * @return
	 */
	public Optional<AudioTrack> getAudioTrack()
	{
		return this.audioTrack;
	}

	/**
	 * Gets whether or not this AudioOption has an audio track path
	 * 
	 * @return
	 */
	public boolean hasAudioTrackPath()
	{
		return this.audioTrackFilePath.isSome();
	}
}
