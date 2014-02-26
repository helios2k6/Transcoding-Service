package com.nlogneg.transcodingService.encoding.mkv;

import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.info.mkv.MKVInfo;
import com.nlogneg.transcodingService.request.incoming.Request;

/**
 * Represents an encoding job for an MKV file
 * @author anjohnson
 *
 */
public final class MKVEncodingJob extends EncodingJob{
	
	private final MKVInfo mkvInfo;
	private final AudioTrackOption audioTrackOption;
	private final SubtitleTrackOption subtitleTrackOption;
	/**
	 * @param request
	 * @param mkvInfo
	 * @param audioTrackOption
	 * @param subtitleTrackOption
	 */
	public MKVEncodingJob(Request request, MKVInfo mkvInfo,
			AudioTrackOption audioTrackOption,
			SubtitleTrackOption subtitleTrackOption) {
		super(request);
		this.mkvInfo = mkvInfo;
		this.audioTrackOption = audioTrackOption;
		this.subtitleTrackOption = subtitleTrackOption;
	}
	
	/**
	 * @return the mkvInfo
	 */
	public MKVInfo getMkvInfo() {
		return mkvInfo;
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
	
}
