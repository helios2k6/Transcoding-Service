package com.nlogneg.transcodingService.demultiplex.mkv;

import java.nio.file.Path;
import java.util.Map;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.info.mediainfo.Track;
import com.nlogneg.transcodingService.info.mkv.Attachment;

/**
 * Represents the demultiplexing of an MKV file
 * @author anjohnson
 *
 */
public final class DemultiplexMKVJob extends DemultiplexJob{
	private final Map<Attachment, Path> attachmentToOutputMap;
	private final Map<Track, Path> trackToOutputMap;
	
	/**
	 * @param mediaFile
	 * @param attachmentToOutputMap
	 * @param trackToOutputMap
	 */
	public DemultiplexMKVJob(Path mediaFile,
			Map<Attachment, 
			Path> attachmentToOutputMap,
			Map<Track, Path> trackToOutputMap){
		super(mediaFile);
		this.attachmentToOutputMap = attachmentToOutputMap;
		this.trackToOutputMap = trackToOutputMap;
	}
	/**
	 * @return the attachmentToOutputMap
	 */
	public Map<Attachment, Path> getAttachmentToOutputMap(){
		return attachmentToOutputMap;
	}
	/**
	 * @return the trackToOutputMap
	 */
	public Map<Track, Path> getTrackToOutputMap(){
		return trackToOutputMap;
	}
}
