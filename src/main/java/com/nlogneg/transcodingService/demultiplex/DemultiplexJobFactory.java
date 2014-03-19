package com.nlogneg.transcodingService.demultiplex;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.GeneralTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummaryFactory;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.info.mkv.MKVInfo;
import com.nlogneg.transcodingService.info.mkv.MKVInfoFactory;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.utilities.CollectionUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

e.utilities.Optional;

/**
 * A factory class meant to create new DemultiplexJobs
 * @author anjohnson
 *
 */
public final class DemultiplexJobFactory{
	private static final Logger Log = LogManager.getLogger(DemultiplexJobFactory.class);
	
	private enum MediaFileType{
		MKV,
		Other
	}

	/**
	 * Creates a new DemultiplexJob given a request
	 * @param request
	 * @return
	 */
	public static Optional<? extends DemultiplexJob> tryCreateDemultiplexJob(Request request, MediaInfo mediaInfo){
		//Get tracks and summary
		MediaInfoTrackSummary summary = MediaInfoTrackSummaryFactory.getSummary(mediaInfo);

		//Figure out what type of media file this is
		Collection<GeneralTrack> generalTracks = summary.getGeneralTracks();
		MediaFileType type = detectMediaFileType(generalTracks);

		switch(type){
		case MKV:
			return Optional.make(createDemultiplexMKVJob(request, mediaInfo, summary));
		case Other:
			return Optional.make(new NoOpDemultiplexJob(null, null));
		default:
			throw new RuntimeException("Unknown media type detected");
		}
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
	
	private static DemultiplexMKVJob createDemultiplexMKVJob(
			Request request, 
			MediaInfo mediaInfo,
			MediaInfoTrackSummary summary){
		
		Path sourceFile = Paths.get(request.getSourceFile());
		Optional<MKVInfo> infoOptional = MKVInfoFactory.tryGetMKVInfo(sourceFile);
		
		//We can't get the info for some reason
		if(infoOptional.isNone()){
			Log.error("Could not create DemultiplexMKVJob for: " + request.getSourceFile());
			return null;
		}
		
		MKVInfo info = infoOptional.getValue();
		
		Collection<Attachment> allAttachments = info.getAttachments();
		Collection<Attachment> fontAttachments = MKVDemultiplexingUtilities.getFontAttachments(allAttachments);
		
		
		throw new RuntimeException("TODO");
	}
	
	private static Map<AudioTrack, Path> deduceAudioTracks(Request request, MediaInfo mediaInfo, MediaInfoTrackSummary summary){
		throw new RuntimeException("TODO");
	}
	
	private static Map<TextTrack, Path> deduceSubtitleTracks(Request request, MediaInfo mediaInfo, MediaInfoTrackSummary summary){
		throw new RuntimeException("TODO");
	}
}
