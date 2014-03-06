package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.util.Collection;
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
	
	@Override
	public List<String> getEncoderArguments(EncodingJob job, Path outputFile) {
		List<String> arguments = new LinkedList<String>();
		
		//add process name
		arguments.add(0, SystemUtilities.getX264ProcessName());
		
		//add demultiplexer
		arguments.add(DemuxerArgument);
		arguments.add(Y4mDemuxer);
		
		//add encoding options
		for(String s : getEncodingOptions(job)){
			arguments.add(s);
		}
		
		//Add output
		arguments.add(OutputFileArgument);
		arguments.add(outputFile.toAbsolutePath().toString());
		
		//Add input stdin
		arguments.add(StandardInput);
		
		return arguments;
	}
	
	private static Collection<String> getEncodingOptions(EncodingJob job){
		//TODO: Have to finish this
		throw new NotImplementedException();
	}
}
