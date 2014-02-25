package com.nlogneg.transcodingService.transcoding.mkv.demultiplex;

import java.util.Set;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummaryFactory;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;

public abstract class DemultiplexMKVAudioSubtitleTrackBase extends DemultiplexMKVMediaTrackCommand{
	/**
	 * Get the media tracks that this particular demultiplexer is interested in
	 * @param summary The summary object
	 * @return A set of media tracks
	 */
	protected abstract Set<? extends MediaTrack> getMediaTracks(MediaInfoTrackSummary summary);
	
	@Override
	protected final MediaTrack getTrackToDemultiplex(MediaInfo mediaInfo){
		return deduceMostLikelyTrack(mediaInfo);
	}
	
	/**
	 * Attempts to deduce the "best" track to use given the media info.
	 * 
	 * The rules of selecting an appropriate track are as follows:
	 * - Japanese tracks take highest priority
	 * - The track with the smallest ID number is preferred
	 * 
	 * @param mediaInfo The media info
	 * @return which track to use or null if no suitable track could be found
	 */
	private MediaTrack deduceMostLikelyTrack(MediaInfo mediaInfo){
		MediaInfoTrackSummary summary = MediaInfoTrackSummaryFactory.getSummary(mediaInfo);

		Set<? extends MediaTrack> mediaTracks = getMediaTracks(summary);
		MediaTrack selectedTrack = null;

		for(MediaTrack track : mediaTracks){
			if(selectedTrack == null){
				selectedTrack = track;
			}else{
				//See if the selected track is Japanese
				if(isTrackJapanese(selectedTrack)){
					/*
					 * If so, then the only way we'll unseat the previous
					 * selection is if the track ID is lower
					 */
					if(track.getId() < selectedTrack.getId()){
						selectedTrack = track;
					}
				}else{
					/*
					 * OK well now there's two ways to unseat the previous track:
					 * 
					 * 1. The candidate track is Japanese
					 * 2. The candidate track has a lower ID number 
					 */
					
					if(isTrackJapanese(track) || track.getId() < selectedTrack.getId()){
						selectedTrack = track;
					}
				}
			}
		}

		return selectedTrack;
	}
	
	private static boolean isTrackJapanese(MediaTrack track){
		JapaneseTrackVisitor visitor = new JapaneseTrackVisitor();
		track.accept(visitor);
		return visitor.getIsJapanese();
	}
}
