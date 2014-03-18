package com.nlogneg.transcodingService.demultiplex;

import java.util.Collection;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mediainfo.GeneralTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.Track;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.utilities.CollectionUtilities;

/**
 * A factory class meant to create new DemultiplexJobs
 * @author anjohnson
 *
 */
public final class DemultiplexJobFactory{
	private enum MediaFileType{
		MKV,
		Other
	}

	/**
	 * Creates a new DemultiplexJob given a request
	 * @param request
	 * @return
	 */
	public static DemultiplexJob createDemultiplexJob(Request request, MediaInfo mediaInfo){
		//Get tracks and summary
		Collection<Track> tracks = getTracks(mediaInfo);
		MediaInfoTrackSummary summary = getSummary(tracks);

		//Figure out what type of media file this is
		Collection<GeneralTrack> generalTracks = summary.getGeneralTracks();
		MediaFileType type = detectMediaFileType(generalTracks);

		switch(type){
		case MKV:
			//Detect tracks to extract and create MKV job
		case Other:
			return new NoOpDemultiplexJob(null, null);
		default:
			throw new RuntimeException("Unknown media type detected");
		}
	}

	private static Collection<Track> getTracks(MediaInfo mediaInfo){
		return mediaInfo.getFile().getTracks();
	}

	private static MediaInfoTrackSummary getSummary(Collection<Track> tracks){
		MediaInfoTrackSummary summary = new MediaInfoTrackSummary();
		for(Track t : tracks){
			t.accept(summary);
		}

		return summary;
	}

	private static MediaFileType detectMediaFileType(Collection<GeneralTrack> tracks){
		if(tracks.size() < 1){
			return MediaFileType.Other;
		}

		GeneralTrack track = CollectionUtilities.first(tracks);
		String format = track.getFormat();

		if(format != null && format.equalsIgnoreCase("Matroska")){
			return MediaFileType.MKV;
		}

		return MediaFileType.Other;
	}
	
	private static DemultiplexMKVJob createDemultiplexMKVJob(Request request, MediaInfo mediaInfo){
		
		throw new RuntimeException("TODO");
	}
}
