package com.nlogneg.transcodingService.demultiplex;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.GeneralTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummaryFactory;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.info.mkv.MKVInfo;
import com.nlogneg.transcodingService.info.mkv.MKVInfoFactory;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.utilities.CollectionUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.Projections;

import fj.F;

public class DemultiplexJobFactory{

	private static final Logger Log = LogManager.getLogger(DemultiplexJobFactory.class);
	private static final AtomicInteger IdSeed = new AtomicInteger();

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

		F<Attachment, Attachment> id = Projections.identity();
		F<Attachment, Path> valueProj = new F<Attachment, Path>(){
			public Path f(Attachment a){
				return Paths.get(a.getFileName());
			}
		};

		Map<Attachment, Path> attachmentMap = CollectionUtilities.toMap(allAttachments, id, valueProj);
		Map<Attachment, Path> fontAttachmentMap = CollectionUtilities.toMap(fontAttachments, id, valueProj);
		Map<AudioTrack, Path> audioTrackMap = deduceAudioTracks(request, mediaInfo, summary);
		Map<TextTrack, Path> subtitleTrackMap = deduceSubtitleTracks(request, mediaInfo, summary);

		return new DemultiplexMKVJob(sourceFile, mediaInfo, attachmentMap, fontAttachmentMap, audioTrackMap, subtitleTrackMap);
	}

	private static <T extends MediaTrack> Map<T, Path> deduceTrack(Request request, MediaInfo mediaInfo, Collection<T> tracks){
		T chosenTrack = MKVDemultiplexingUtilities.deduceMostLikelyTrack(tracks);

		Map<T, Path> audioTrackMap = new HashMap<>();

		Path sourceFile = Paths.get(request.getSourceFile());
		StringBuilder builder = new StringBuilder();
		
		builder
			.append(sourceFile.getFileName().toString())
			.append("_temp_")
			.append(IdSeed.incrementAndGet())
			.append("_.")
			.append(chosenTrack.getFormat());

		audioTrackMap.put(chosenTrack, Paths.get(builder.toString()));

		return audioTrackMap;
	}

	private static Map<AudioTrack, Path> deduceAudioTracks(Request request, MediaInfo mediaInfo, MediaInfoTrackSummary summary){
		return deduceTrack(request, mediaInfo, summary.getAudioTracks());
	}

	private static Map<TextTrack, Path> deduceSubtitleTracks(Request request, MediaInfo mediaInfo, MediaInfoTrackSummary summary){
		return deduceTrack(request, mediaInfo, summary.getSubtitleTracks());
	}
}
