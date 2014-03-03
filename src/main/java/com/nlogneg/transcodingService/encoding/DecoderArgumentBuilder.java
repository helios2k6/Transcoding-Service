package com.nlogneg.transcodingService.encoding;

import java.util.List;

/**
 * Represents an object that will determine the decoding arguments for a 
 * particular encoding job
 * @author Andrew
 *
 */
public interface DecoderArgumentBuilder{
	/**
	 * Get the decoding arguments for a particular encoding job
	 * @param job
	 * @return
	 */
	public List<String> getDecoderArguments(EncodingJob job);
}
