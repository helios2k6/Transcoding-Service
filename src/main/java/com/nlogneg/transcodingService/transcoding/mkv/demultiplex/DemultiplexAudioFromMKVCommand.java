package com.nlogneg.transcodingService.transcoding.mkv.demultiplex;

import java.util.Set;

import com.nlogneg.transcodingService.mediaInfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.mediaInfo.MediaTrack;

/**
 * Demultiplexes the audio track from an MKV file
 * @author anjohnson
 *
 */
public class DemultiplexAudioFromMKVCommand extends DemultiplexMKVAudioSubtitleTrackBase{
	protected String getOutputFileName(String fileName, MediaTrack mediaTrack){
		return fileName + "_audio." + mediaTrack.getFormat(); 
	}

	@Override
	protected Set<? extends MediaTrack> getMediaTracks(MediaInfoTrackSummary summary) {
		return summary.getAudioTracks();
	}
}
