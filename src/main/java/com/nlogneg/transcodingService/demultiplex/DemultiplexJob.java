package com.nlogneg.transcodingService.demultiplex;

import java.nio.file.Path;

import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.Tuple;

/**
 * Represents what needs to be demultiplexed and extracted from a file
 * @author anjohnson
 *
 */
public abstract class DemultiplexJob{
	private final Path mediaFile;
	private final MediaInfo mediaInfo;
	private final Optional<Tuple<AudioTrack, Path>> audioTrack;
	private final Optional<Tuple<TextTrack, Path>> subtitleTrack;

	/**
	 * @param mediaFile
	 * @param mediaInfo
	 * @param audioTrack
	 * @param subtitleTrack
	 */
	public DemultiplexJob(Path mediaFile, MediaInfo mediaInfo,
			Optional<Tuple<AudioTrack, Path>> audioTrack,
			Optional<Tuple<TextTrack, Path>> subtitleTrack) {
		this.mediaFile = mediaFile;
		this.mediaInfo = mediaInfo;
		this.audioTrack = audioTrack;
		this.subtitleTrack = subtitleTrack;
	}

	/**
	 * @return the mediaFile
	 */
	public Path getMediaFile() {
		return mediaFile;
	}

	/**
	 * @return the mediaInfo
	 */
	public MediaInfo getMediaInfo() {
		return mediaInfo;
	}

	/**
	 * @return the audioTrack
	 */
	public Optional<Tuple<AudioTrack, Path>> getAudioTrack() {
		return audioTrack;
	}

	/**
	 * @return the subtitleTrack
	 */
	public Optional<Tuple<TextTrack, Path>> getSubtitleTrack() {
		return subtitleTrack;
	}
}
