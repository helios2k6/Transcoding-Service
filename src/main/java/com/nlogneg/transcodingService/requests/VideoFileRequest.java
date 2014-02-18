package com.nlogneg.transcodingService.requests;

import java.io.Serializable;

/**
 * Represents a request specifically for a video file
 * @author anjohnson
 *
 */
public final class VideoFileRequest extends Request implements Serializable {
	
	/**
	 * Generated serialization version ID
	 */
	private static final long serialVersionUID = 7069787159367388427L;

	
	/**
	 * Constructs a new request for a video file
	 * @param filePath The file path 
	 * @param outputPath The output path
	 * @param outputType The output type
	 */
	public VideoFileRequest(String filePath, String outputPath, OutputType outputType) {
		super(filePath, outputPath, outputType);
	}
	
}
