package com.nlogneg.transcodingService.encoding;

import java.util.LinkedList;
import java.util.List;

import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Encapsulates the organization of ffmpeg decoding arguments
 * @author Andrew
 *
 */
public final class FFMPEGDecodingArgumentBuilder{
	
	private static final String StandardOutArgument = "-";
	private static final String VideoCodecArgument = "-codec:v";
	private static final String MuteAudioArgument = "-an";
	private static final String InputFileArgument = "-i";
	private static final String PixelFormatArgument = "-pix_fmt";
	private static final String FormatArgument = "-f";
	
	private static final String RawVideoFormat = "rawvideo";
	private static final String Yuv4MpegPipeFormat = "yuv4mpegpipe";
	private static final String Yuv420pPixelFormat = "yuv420p";
	
	private final String inputFile;
	
	private final List<String> decodingOptions;
	private final List<String> filterOptions;
	private final List<String> outputOptions;
	
	/**
	 * @param inputFile
	 * @param decodingOptions
	 * @param filterOptions
	 * @param outputOptions
	 */
	public FFMPEGDecodingArgumentBuilder(
			String inputFile,
			List<String> decodingOptions, 
			List<String> filterOptions,
			List<String> outputOptions){
		
		this.inputFile = inputFile;
		this.decodingOptions = decodingOptions;
		this.filterOptions = filterOptions;
		this.outputOptions = outputOptions;
	}
	
	private List<String> calculateArgumentArray(){
		List<String> arguments = new LinkedList<String>();
		
		addFfmpegProcessName(arguments);
		addDecodingOptions(arguments);
		muteAudio(arguments);
		addInputFile(arguments);
		addFilterOptions(arguments);
		addVideoCodecFormat(arguments);
		addMediaContainerFormat(arguments);
		addPixelFormat(arguments);
		addOutputOptions(arguments);
		addStandardOut(arguments);
		
		return arguments;
	}

	private void addFfmpegProcessName(List<String> arguments){
		arguments.add(0, SystemUtilities.getFFMPEGProcessName());
	}
	
	private void addStandardOut(List<String> arguments){
		arguments.add(StandardOutArgument);
	}
	
	private void addOutputOptions(List<String> arguments){
		//Output options
		for(String s : outputOptions){
			arguments.add(s);
		}
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

	private void addFilterOptions(List<String> arguments){
		//Filter options
		for(String s : filterOptions){
			arguments.add(s);
		}
	}

	private void addInputFile(List<String> arguments){
		//Input file
		arguments.add(InputFileArgument);
		arguments.add(inputFile);
	}

	private void muteAudio(List<String> arguments){
		//Mute audio
		arguments.add(MuteAudioArgument);
	}

	private void addDecodingOptions(List<String> arguments){
		//Decoding options
		for(String s : decodingOptions){
			arguments.add(s);
		}
	}
	
	/**
	 * Gets the fully resolved ffmpeg argument array, with the ffmpeg process name
	 * @return
	 */
	public List<String> getArguments(){
		return calculateArgumentArray();
	}
}
