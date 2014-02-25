package com.nlogneg.transcodingService.transcoding.mkv.demultiplex;

import java.util.Set;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;

/**
 * Demultiplexes the subtitle track
 * @author Andrew
 *
 */
public class DemultiplexSubtitleFromMKVCommand extends DemultiplexMKVAudioSubtitleTrackBase{

	@Override
	protected Set<? extends MediaTrack> getMediaTracks(MediaInfoTrackSummary summary) {
		return summary.getSubtitleTracks();
	}

	@Override
	protected String getOutputFileName(String fileName, MediaTrack mediaTrack) {
		return fileName + "_subtitle." + mediaTrack.getFormat();
	}

}
