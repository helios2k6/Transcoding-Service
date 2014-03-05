package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.request.incoming.Request;

/**
 * Represents a transcoding job
 * @author anjohnson
 *
 */
public final class EncodingJob{
	private static final AtomicLong IDSeed = new AtomicLong(0);
	
	private final long id;
	private final Request request;
	private final MediaInfo mediaInfo;
	private final AudioTrackOption audioTrackOption;
	private final SubtitleTrackOption subtitleTrackOption;

	/**
	 * @param request
	 * @param mediaInfo
	 * @param audioTrackOption
	 * @param subtitleTrackOption
	 */
	public EncodingJob(Request request, MediaInfo mediaInfo,
			AudioTrackOption audioTrackOption,
			SubtitleTrackOption subtitleTrackOption) {
		this.id = IDSeed.incrementAndGet();
		this.request = request;
		this.mediaInfo = mediaInfo;
		this.audioTrackOption = audioTrackOption;
		this.subtitleTrackOption = subtitleTrackOption;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * @return the mediaInfo
	 */
	public MediaInfo getMediaInfo() {
		return mediaInfo;
	}

	/**
	 * @return the audioTrackOption
	 */
	public AudioTrackOption getAudioTrackOption() {
		return audioTrackOption;
	}

	/**
	 * @return the subtitleTrackOption
	 */
	public SubtitleTrackOption getSubtitleTrackOption() {
		return subtitleTrackOption;
	}
	
	/**
	 * Gets the source file as a Path
	 * @return
	 */
	public Path getSourceFilePath(){
		return Paths.get(request.getSourceFile());
	}
	
	/**
	 * Gets the destination file as a Path
	 * @return
	 */
	public Path getDestinationFilePath(){
		return Paths.get(request.getDestinationFile());
	}
}
