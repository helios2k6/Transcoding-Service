package com.nlogneg.transcodingService.encoding.ffmpeg;

import java.util.LinkedList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.nlogneg.transcodingService.encoding.DecoderArgumentBuilder;
import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.encoding.WidthHeightTuple;
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
	
	private List<String> calculateArgumentArray(EncodingJob encodingJob){
		List<String> arguments = new LinkedList<String>();
		
		addFfmpegProcessName(arguments);
		
		muteAudio(arguments);
		addInputFile(arguments, encodingJob);
		
		addFilters(arguments, encodingJob);
		
		addVideoCodecFormat(arguments);
		addMediaContainerFormat(arguments);
		addPixelFormat(arguments);
		
		//Must be last
		addStandardOut(arguments);
		
		return arguments;
	}
	
	private void addFilters(List<String> arguments, EncodingJob job){
		
		
		throw new NotImplementedException();
	}
	
	/**
	 * So, since the user can actually specify two options that may contradict
	 * one another, we'll resolve them here.
	 * 
	 * If the user wants to force a specific resolution, then we will obey that
	 * @param job
	 * @return
	 */
	private WidthHeightTuple calculateWidthHeightTuple(EncodingJob job){
		
		return null;
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
