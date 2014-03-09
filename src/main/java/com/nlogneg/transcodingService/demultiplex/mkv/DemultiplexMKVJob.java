package com.nlogneg.transcodingService.demultiplex.mkv;

import java.nio.file.Path;
import java.util.Map;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mkv.Attachment;

/**
 * Represents the demultiplexing of an MKV file
 * @author anjohnson
 *
 */
public final class DemultiplexMKVJob extends DemultiplexJob{
	private final Map<Attachment, Path> attachmentMap;
	private final Map<Attachment, Path> fontAttachmentMap;
	private final Map<AudioTrack, Path> audioTrackMap;
	private final Map<TextTrack, Path> subtitleTrackMap;

	/**
	 * @param mediaFile
	 * @param mediaInfo
	 * @param attachmentMap
	 * @param fontAttachmentMap
	 * @param audioTrackMap
	 * @param subtitleTrackMap
	 */
	public DemultiplexMKVJob(Path mediaFile, MediaInfo mediaInfo,
			Map<Attachment, Path> attachmentMap,
			Map<Attachment, Path> fontAttachmentMap,
			Map<AudioTrack, Path> audioTrackMap,
			Map<TextTrack, Path> subtitleTrackMap) {
		super(mediaFile, mediaInfo);
		this.attachmentMap = attachmentMap;
		this.fontAttachmentMap = fontAttachmentMap;
		this.audioTrackMap = audioTrackMap;
		this.subtitleTrackMap = subtitleTrackMap;
	}

	/**
	 * @return the attachmentToOutputMap
	 */
	public Map<Attachment, Path> getAttachmentMap(){
		return attachmentMap;
	}
	
	/**
	 * @return the fontAttachmentMap
	 */
	public Map<Attachment, Path> getFontAttachmentMap() {
		return fontAttachmentMap;
	}

	/**
	 * @return the audioTrackMap
	 */
	public Map<AudioTrack, Path> getAudioTrackMap() {
		return audioTrackMap;
	}

	/**
	 * @return the subtitleTrackMap
	 */
	public Map<TextTrack, Path> getSubtitleTrackMap() {
		return subtitleTrackMap;
	}
}
