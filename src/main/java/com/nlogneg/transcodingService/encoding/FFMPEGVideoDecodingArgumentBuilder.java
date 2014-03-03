package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Encapsulates the organization of ffmpeg decoding arguments
 * @author Andrew
 *
 */
public final class FFMPEGVideoDecodingArgumentBuilder implements DecoderArgumentBuilder{
	private static final Logger Log = LogManager.getLogger(FFMPEGVideoDecodingArgumentBuilder.class);
	
	private static final String SimpleVideoFilterArgument = "-vf";
	private static final String StandardOutArgument = "-";
	private static final String VideoCodecArgument = "-codec:v";
	private static final String MuteAudioArgument = "-an";
	private static final String InputFileArgument = "-i";
	private static final String PixelFormatArgument = "-pix_fmt";
	private static final String FormatArgument = "-f";
	
	private static final String RawVideoFormat = "rawvideo";
	private static final String Yuv4MpegPipeFormat = "yuv4mpegpipe";
	private static final String Yuv420pPixelFormat = "yuv420p";
	
	private List<String> calculateArgumentArray(EncodingJob encodingJob){
		List<String> arguments = new LinkedList<String>();
		
		addFfmpegProcessName(arguments);
		
		muteAudio(arguments);
		addInputFile(arguments, encodingJob);
		
		maybeAddSubtitleFilter(arguments, encodingJob);
		
		addVideoCodecFormat(arguments);
		addMediaContainerFormat(arguments);
		addPixelFormat(arguments);
		
		//Must be last
		addStandardOut(arguments);
		
		return arguments;
	}
	
	private void maybeAddSubtitleFilter(List<String> arguments, EncodingJob job){
		SubtitleTrackOption subtitleOption = job.getSubtitleTrackOption();
		if(subtitleOption.getEncodingActions().contains(EncodingAction.Encode)){
			Optional<Path> extractedOptionalSubtitleFile = subtitleOption.getTextTrackFilePath();
			if(extractedOptionalSubtitleFile.isSome()){
				Path extractedSubtitleFile = extractedOptionalSubtitleFile.getValue();
				
				StringBuilder argBuilder = new StringBuilder();
				argBuilder.append("\"").append("ass=").append(extractedSubtitleFile.toAbsolutePath().toString()).append("\"");
				
				arguments.add(SimpleVideoFilterArgument);
				arguments.add(argBuilder.toString());
			}else{
				Log.error("Subtitle track option was ENCODE but no extracted subtitle track could be found");
			}
		}
	}
	
	private void addFfmpegProcessName(List<String> arguments){
		arguments.add(0, SystemUtilities.getFFMPEGProcessName());
	}
	
	private void addInputFile(List<String> arguments, EncodingJob job){
		arguments.add(InputFileArgument);
		arguments.add(job.getRequest().getSourceFile().getPath());
	}
	
	private void addStandardOut(List<String> arguments){
		arguments.add(StandardOutArgument);
	}
	
	private void addPixelFormat(List<String> arguments){
		//Pixel format
		arguments.add(PixelFormatArgument);
		arguments.add(Yuv420pPixelFormat);
	}

	private void addMediaContainerFormat(List<String> arguments){
		//Output format container
		arguments.add(FormatArgument);
		arguments.add(Yuv4MpegPipeFormat);
	}

	private void addVideoCodecFormat(List<String> arguments){
		//Output video codec selection
		arguments.add(VideoCodecArgument);
		arguments.add(RawVideoFormat);
	}

	private void muteAudio(List<String> arguments){
		//Mute audio
		arguments.add(MuteAudioArgument);
	}

	@Override
	public List<String> getDecoderArguments(EncodingJob job) {
		return calculateArgumentArray(job);
	}
}
