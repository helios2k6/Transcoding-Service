package com.nlogneg.transcodingService.encoding;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

/**
 * Encodes a media file
 * @author anjohnson
 *
 */
public abstract class EncodeMediaFileCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(EncodeMediaFileCommand.class);
	private static final String StandardOutputArgument = "-";
	
	public final void execute(INotification notification){
		
	}
	
	/**
	 * Get the decoder arguments for the FFMPEG Process 
	 * @param job
	 * @return
	 */
	protected abstract List<String> getDecoderArguments(EncodingJob job);
	
	/**
	 * Get the encoder arguments for the X264 Process
	 * @param job
	 * @return
	 */
	protected abstract List<String> getEncoderArguments(EncodingJob job);
	
	/**
	 * Get the mutltiplexing arguments for the FFMpeg Process
	 * @param job
	 * @return
	 */
	protected abstract List<String> getMultiplexingArguments(EncodingJob job);
	
	
}
