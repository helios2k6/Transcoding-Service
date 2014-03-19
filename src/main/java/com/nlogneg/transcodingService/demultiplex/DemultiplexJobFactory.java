package com.nlogneg.transcodingService.demultiplex;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
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
import com.nlogneg.transcodingService.info.mediainfo.MediaTrackUtils;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.info.mkv.MKVInfo;
import com.nlogneg.transcodingService.info.mkv.MKVInfoFactory;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.request.incoming.Selector;
import com.nlogneg.transcodingService.utilities.CollectionUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.Projections;
import com.nlogneg.transcodingService.utilities.Tuple;

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
			return Optional.make(new NoOpDemultiplexJob());
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
		Optional<Tuple<AudioTrack, Path>> audioTrackMap = deduceAudioTrack(request, summary);
		Optional<Tuple<TextTrack, Path>> subtitleTrackMap = deduceSubtitleTrack(request, summary);

		return new DemultiplexMKVJob(
				sourceFile, 
				mediaInfo, 
				audioTrackMap,
				subtitleTrackMap,
				attachmentMap, 
				fontAttachmentMap);
	}

	private static <T extends MediaTrack> Optional<Tuple<T, Path>> createTuple(Request request, T t){
		Path sourceFile = Paths.get(request.getSourceFile());
		StringBuilder builder = new StringBuilder();
		
		builder
			.append(sourceFile.getFileName().toString())
			.append("_temp_")
			.append(IdSeed.incrementAndGet())
			.append("_.")
			.append(t.getFormat());
		
		return Optional.make(new Tuple<T, Path>(t, Paths.get(builder.toString())));
	}
	
	private static <T extends MediaTrack> Optional<Tuple<T, Path>> deduceTrack(Request request, Collection<T> tracks){
		Optional<T> chosenTrack = MKVDemultiplexingUtilities.tryDeduceMostLikelyTrack(tracks);
		if(chosenTrack.isNone()){
			return Optional.none();
		}
		
		return createTuple(request, chosenTrack.getValue());
	}

	private static Optional<Tuple<AudioTrack, Path>> deduceAudioTrack(Request request, MediaInfoTrackSummary summary){
		Selector selector = request.getSelector();
		if(selector.isForceUseAudioTrack()){
			int audioTrack = selector.getAudioTrack();
			Optional<AudioTrack> track = MediaTrackUtils.tryGetMediaTrackById(summary.getAudioTracks(), audioTrack);
			
			if(track.isSome()){
				return createTuple(request, track.getValue());
			}
		}
		return deduceTrack(request, summary.getAudioTracks());
	}

	private static Optional<Tuple<TextTrack, Path>> deduceSubtitleTrack(Request request, MediaInfoTrackSummary summary){
		return deduceTrack(request, summary.getSubtitleTracks());
	}
}
