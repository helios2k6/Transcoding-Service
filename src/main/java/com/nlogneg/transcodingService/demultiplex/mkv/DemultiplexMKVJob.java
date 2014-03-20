package com.nlogneg.transcodingService.demultiplex.mkv;

import java.nio.file.Path;
import java.util.Map;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.Tuple;

/**
 * Represents the demultiplexing of an MKV file
 * 
 * @author anjohnson
 * 
 */
public final class DemultiplexMKVJob extends DemultiplexJob
{
	private final Map<Attachment, Path> attachmentMap;
	private final Map<Attachment, Path> fontAttachmentMap;

	/**
	 * @param mediaFile
	 * @param mediaInfo
	 * @param audioTrack
	 * @param subtitleTrack
	 * @param attachmentMap
	 * @param fontAttachmentMap
	 */
	public DemultiplexMKVJob(final Path mediaFile, final MediaInfo mediaInfo,
			final Optional<Tuple<AudioTrack, Path>> audioTrack,
			final Optional<Tuple<TextTrack, Path>> subtitleTrack,
			final Map<Attachment, Path> attachmentMap,
			final Map<Attachment, Path> fontAttachmentMap)
	{
		super(mediaFile, mediaInfo, audioTrack, subtitleTrack);
		this.attachmentMap = attachmentMap;
		this.fontAttachmentMap = fontAttachmentMap;
	}

	/**
	 * @return the attachmentToOutputMap
	 */
	public Map<Attachment, Path> getAttachmentMap()
	{
		return this.attachmentMap;
	}

	/**
	 * @return the fontAttachmentMap
	 */
	public Map<Attachment, Path> getFontAttachmentMap()
	{
		return this.fontAttachmentMap;
	}
}
