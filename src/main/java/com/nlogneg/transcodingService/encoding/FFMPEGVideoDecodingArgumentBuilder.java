package com.nlogneg.transcodingService.encoding;

import java.util.LinkedList;
import java.util.List;

import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Encapsulates the organization of ffmpeg decoding arguments
 * @author Andrew
 *
 */
public final class FFMPEGVideoDecodingArgumentBuilder implements DecoderArgumentBuilder{
	
	private static final String StandardOutArgument = "-";
	private static final String VideoCodecArgument = "-codec:v";
	private static final String MuteAudioArgument = "-an";
	private static final String InputFileArgument = "-i";
	private static final String PixelFormatArgument = "-pix_fmt";
	private static final String FormatArgument = "-f";
	
	private static final String RawVideoFormat = "rawvideo";
	private static final String Yuv4MpegPipeFormat = "yuv4mpegpipe";
	private static final String Yuv420pPixelFormat = "yuv420p";
	
	private List<String> calculateArgumentArray(){
		List<String> arguments = new LinkedList<String>();
		
		addFfmpegProcessName(arguments);
		muteAudio(arguments);
		addVideoCodecFormat(arguments);
		addMediaContainerFormat(arguments);
		addPixelFormat(arguments);
		addStandardOut(arguments);
		
		return arguments;
	}

	private void addFfmpegProcessName(List<String> arguments){
		arguments.add(0, SystemUtilities.getFFMPEGProcessName());
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
		return calculateArgumentArray();
	}
}
