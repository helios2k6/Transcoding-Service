package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Represents an argument builder for x264
 * @author Andrew
 *
 */
public final class X264EncodingArgumentBuilder implements EncoderArgumentBuilder{
	private static final String DemuxerArgument = "--demuxer";
	private static final String OutputFileArgument = "--output";
	
	private static final String Y4mDemuxer = "y4m";
	private static final String StandardInput = "-";
	
	private final List<String> encodingOptions;
	private final String outputFile;
	/**
	 * @param encodingOptions
	 */
	public X264EncodingArgumentBuilder(
			String outputFile, 
			List<String> encodingOptions){
		this.encodingOptions = encodingOptions;
		this.outputFile = outputFile;
	}
	
	/**
	 * Gets the arguments, without the x264 process name
	 * @return
	 */
	public List<String> getArguments(){
		List<String> arguments = new LinkedList<String>();
		
		//add process name
		arguments.add(0, SystemUtilities.getX264ProcessName());
		
		//add demultiplexer
		arguments.add(DemuxerArgument);
		arguments.add(Y4mDemuxer);
		
		//add encoding options
		for(String s : encodingOptions){
			arguments.add(s);
		}
		
		//Add output
		arguments.add(OutputFileArgument);
		arguments.add(outputFile);
		
		//Add input stdin
		arguments.add(StandardInput);
		
		return arguments;
	}

	@Override
	public List<String> getEncoderArguments(EncodingJob job, Path outputFile) {
		throw new NotImplementedException();
	}
}
