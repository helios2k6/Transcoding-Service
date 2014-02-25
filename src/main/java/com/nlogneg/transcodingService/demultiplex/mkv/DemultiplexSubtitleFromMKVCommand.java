package com.nlogneg.transcodingService.demultiplex.mkv;

import java.nio.file.Path;
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
	protected String getOutputFileName(Path filePath, MediaTrack mediaTrack) {
		return filePath.getFileName() + "_subtitle." + mediaTrack.getFormat();
	}

}
