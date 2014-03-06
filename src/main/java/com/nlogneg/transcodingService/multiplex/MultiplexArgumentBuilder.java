package com.nlogneg.transcodingService.multiplex;

import java.util.List;

import com.nlogneg.transcodingService.encoding.EncodingJob;

/**
 * Represents an object that can get the multiplexing arguments for an 
 * Encoding job
 * @author Andrew
 *
 */
public interface MultiplexArgumentBuilder{
	List<String> getMultiplexingArguments(EncodingJob job);
}
