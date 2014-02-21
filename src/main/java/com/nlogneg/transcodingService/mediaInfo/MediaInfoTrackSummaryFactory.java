package com.nlogneg.transcodingService.mediaInfo;

import java.util.List;

/**
 * A factory class that generates media info track summaries
 * @author anjohnson
 *
 */
public class MediaInfoTrackSummaryFactory{
	
	/**
	 * Get the media info track summary for the given media info
	 * @param mediaInfo The media info
	 * @return The media info track summary
	 */
	public static MediaInfoTrackSummary getSummary(MediaInfo mediaInfo){
		if(mediaInfo == null){
			throw new NullPointerException();
		}
		
		List<Track> tracks = mediaInfo.getFile().getTracks();
		MediaInfoTrackSummary summary = new MediaInfoTrackSummary();
		for(Track track : tracks){
			track.accept(summary);
		}
		
		return summary;
	}
}
