package com.nlogneg.transcodingService.multiplex.mp4;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import com.nlogneg.transcodingService.multiplex.MultiplexArgumentBuilder;
import com.nlogneg.transcodingService.multiplex.MultiplexJob;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

public class MP4BoxArgumentBuilder implements MultiplexArgumentBuilder{

	private static final String AddArg = "-add";
	private static final String NewArg = "-new";
	
	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.multiplex.MultiplexArgumentBuilder#getMultiplexingArguments(com.nlogneg.transcodingService.encoding.EncodingJob)
	 */
	@Override
	public List<String> getMultiplexingArguments(MultiplexJob job){
		List<String> args = new LinkedList<String>();
		
		args.add(SystemUtilities.getMP4BoxProcessName());
		
		//Add the video bit stream
		addFile(args, job.getEncodedVideoFile());
		
		//Add audio bit stream if possible
		if(job.getAudioTrackOption().hasAudioTrackPath()){
			addFile(args, job.getAudioTrackOption().getAudioTrackFilePath().getValue());
		}
		
		//Add the output file
		addNewFileArg(args, job.getDestinationFile());
		
		return args;
	}
	
	private static void addFile(List<String> args, Path file){
		args.add(AddArg);
		args.add(file.toAbsolutePath().toString());
	}

	private static void addNewFileArg(List<String> args, Path file){
		args.add(NewArg);
		args.add(file.toAbsolutePath().toString());
	}
}
