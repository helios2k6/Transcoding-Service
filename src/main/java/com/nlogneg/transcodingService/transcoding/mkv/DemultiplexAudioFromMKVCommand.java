package com.nlogneg.transcodingService.transcoding.mkv;

import java.util.Set;

import com.nlogneg.transcodingService.mediaInfo.AudioTrack;
import com.nlogneg.transcodingService.mediaInfo.MediaInfo;
import com.nlogneg.transcodingService.mediaInfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.mediaInfo.MediaInfoTrackSummaryFactory;
import com.nlogneg.transcodingService.mediaInfo.MediaTrack;

/**
 * Demultiplexes the audio track from an MKV file
 * @author anjohnson
 *
 */
public class DemultiplexAudioFromMKVCommand extends DemultiplexMkvMediaTrackCommand{
	protected MediaTrack getTrackToDemultiplex(MediaInfo mediaInfo){
		return getAudioTrack(mediaInfo);
	}
	
	protected String getOutputFileName(String fileName, MediaTrack mediaTrack){
		return fileName + "_audio." + mediaTrack.getFormat(); 
	}
	
	/**
	 * Attempts to deduce the "best" audio track to use given the media info.
	 * 
	 * The rules of selecting an appropriate audio track are as follows:
	 * - Japanese tracks take highest priority
	 * - The track with the smallest ID number is preferred
	 * 
	 * @param mediaInfo The media info
	 * @return which track to use or null if no suitable track could be found
	 */
	private static AudioTrack getAudioTrack(MediaInfo mediaInfo){
		MediaInfoTrackSummary summary = MediaInfoTrackSummaryFactory.getSummary(mediaInfo);

		Set<AudioTrack> audioTracks = summary.getAudioTracks();
		AudioTrack selectedAudioTrack = null;

		for(AudioTrack track : audioTracks){
			if(selectedAudioTrack == null){
				//Initial track
				selectedAudioTrack = track;
			}else{
				//See if the selected track is Japanese
				if(isTrackJapanese(selectedAudioTrack)){
					/*
					 * If so, then the only way we'll unseat the previous
					 * selection is if the track ID is lower
					 */
					if(track.getId() < selectedAudioTrack.getId()){
						selectedAudioTrack = track;
					}
				}else{
					/*
					 * OK well now there's two ways to unseat the previous track:
					 * 
					 * 1. The candidate track is Japanese
					 * 2. The candidate track has a lower ID number 
					 */
					
					if(isTrackJapanese(track) || track.getId() < selectedAudioTrack.getId()){
						selectedAudioTrack = track;
					}
				}
			}
		}

		return selectedAudioTrack;
	}

	private static boolean isTrackJapanese(AudioTrack track){
		String language = track.getLanguage();
		if(language != null){
			return language.equalsIgnoreCase("Japanese");
		}

		return false;
	}
}
