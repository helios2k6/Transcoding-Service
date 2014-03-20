package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;

import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents the subtitle track encoding options
 * 
 * @author anjohnson
 * 
 */
public final class SubtitleTrackOption
{
	private final Optional<Path> textTrackFilePath;
	private final EncodingAction encodingActions;
	private final Optional<TextTrack> subtitleTrack;

	/**
	 * @param textTrackFilePath
	 * @param encodingActions
	 */
	public SubtitleTrackOption(final Optional<Path> textTrackFilePath,
			final EncodingAction encodingActions,
			final Optional<TextTrack> subtitleTrack)
	{
		this.textTrackFilePath = textTrackFilePath;
		this.encodingActions = encodingActions;
		this.subtitleTrack = subtitleTrack;
	}

	/**
	 * @return the textTrackFilePath
	 */
	public Optional<Path> getTextTrackFilePath()
	{
		return this.textTrackFilePath;
	}

	/**
	 * @return the encodingActions
	 */
	public EncodingAction getEncodingActions()
	{
		return this.encodingActions;
	}

	/**
	 * Gets the subtitle track
	 * 
	 * @return
	 */
	public Optional<TextTrack> getSubtitleTrack()
	{
		return this.subtitleTrack;
	}
}
