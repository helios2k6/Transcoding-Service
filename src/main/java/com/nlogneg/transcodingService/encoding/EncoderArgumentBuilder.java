package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.util.List;

/**
 * An object that can determine the encoding arguments for an encoding job
 * @author Andrew
 *
 */
public interface EncoderArgumentBuilder{
	/**
	 * Get the encoder arguments 
	 * @param job
	 * @param outputFile
	 * @return
	 */
	public List<String> getEncoderArguments(EncodingJob job, Path outputFile);
}
