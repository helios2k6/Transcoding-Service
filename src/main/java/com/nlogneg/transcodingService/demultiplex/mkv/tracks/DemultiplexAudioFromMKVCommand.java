package com.nlogneg.transcodingService.demultiplex.mkv.tracks;

import java.nio.file.Path;
import java.util.Set;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;

/**
 * Demultiplexes the audio track from an MKV file
 * @author anjohnson
 *
 */
public class DemultiplexAudioFromMKVCommand extends DemultiplexMKVAudioSubtitleTrackBase{
	protected String getOutputFileName(Path filePath, MediaTrack mediaTrack){
		return filePath.getFileName() + "_audio." + mediaTrack.getFormat(); 
	}

	@Override
	protected Set<? extends MediaTrack> getMediaTracks(MediaInfoTrackSummary summary) {
		return summary.getAudioTracks();
	}
}
