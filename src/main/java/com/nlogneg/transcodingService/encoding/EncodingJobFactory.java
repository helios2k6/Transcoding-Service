package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A factory class for creating Encoding Jobs
 * @author anjohnson
 *
 */
public final class EncodingJobFactory{
	
	public EncodingJob createEncodingJob(
			Request request, 
			MediaInfo mediaInfo,
			Optional<AudioTrack> demultiplexAudioTrack,
			Optional<TextTrack> demultiplexSubtitleTrack,
			Optional<Path> demultiplexedAudioTrackFile, 
			Optional<Path> demultiplexedSubtitlePath){
		
		AudioTrackOption audio = deduceAudioTrackOption(demultiplexAudioTrack, demultiplexedAudioTrackFile);
		SubtitleTrackOption subs = deduceSubtitleTrackOption(demultiplexSubtitleTrack, demultiplexedSubtitlePath);
		
		Path videoTrack = generateVideoFileName(request);
		Path audioTrack = null;
		
		if(audio.hasAudioTrackPath() && audio.getEncodingAction() == EncodingAction.Encode){
			audioTrack = generateAudioFileName(request, audio.getAudioTrack().getValue());
		}else if(demultiplexAudioTrack.isSome() && audio.getEncodingAction() == EncodingAction.Multiplex){
			audioTrack = demultiplexedAudioTrackFile.getValue();
		}
		
		return new EncodingJob(request, mediaInfo, audio, subs, videoTrack, audioTrack);
	}
	
	private static AudioTrackOption deduceAudioTrackOption(
			Optional<AudioTrack> audioTrack, 
			Optional<Path> demultiplexAudioTrack){
		
		if(audioTrack.isNone() && demultiplexAudioTrack.isNone()){
			return new AudioTrackOption(
					Optional.<Path>none(), 
					EncodingAction.Ignore, 
					Optional.<AudioTrack>none());
		}
		
		AudioTrack track = audioTrack.getValue();
		EncodingAction action = EncodingAction.Encode;
		
		if(track.getFormat().equalsIgnoreCase("AAC")){
			action = EncodingAction.Multiplex;
		}
		
		return new AudioTrackOption(demultiplexAudioTrack, action, audioTrack);
	}
	
	private static SubtitleTrackOption deduceSubtitleTrackOption(
			Optional<TextTrack> demultiplexSubtitleTrack, 
			Optional<Path> demultiplexedSubtitlePath)
	{
		EncodingAction action = EncodingAction.Ignore;
		if(demultiplexSubtitleTrack.isSome()){
			action = EncodingAction.Encode;
		}
		
		return new SubtitleTrackOption(demultiplexedSubtitlePath, action, demultiplexSubtitleTrack);
	}
	
	private static Path generateVideoFileName(Request request){
		String fileName = Paths.get(request.getSourceFile()).toAbsolutePath().toString();
		return Paths.get(fileName + "_temp_encoded.264");
	}
	
	private static Path generateAudioFileName(Request request, AudioTrack track){
		String fileName = Paths.get(request.getSourceFile()).toAbsolutePath().toString();
		return Paths.get(fileName + "_temp_encoded.m4a");
	}
}
