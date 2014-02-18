package com.nlogneg.transcodingService.requests;

import com.nlogneg.transcodingService.media.EncodingSettings;

/**
 * Represents a transcoding request. 
 * @author anjohnson
 *
 */
public class Request{
	
	private final String filePath;
	private final String outputPath;
	private final EncodingSettings settings;

	/**
	 * Constructs a new, immutable request
	 * @param filePath The file path
	 * @param outputPath The output path
	 * @param settings The encoding settings for this request
	 */
	protected Request(String filePath, String outputPath, EncodingSettings settings){
		this.filePath = filePath;
		this.outputPath = outputPath;
		this.settings = settings;
	}
	
	/**
	 * Get the file path
	 * @return
	 */
	public String getFilePath(){
		return filePath;
	}

	/**
	 * Get the output path
	 * @return
	 */
	public String getOutputPath(){
		return outputPath;
	}
	
	/**
	 * Gets the encoding settings for this request
	 * @return The encoding settings
	 */
	public EncodingSettings getSettings(){
		return settings;
	}
	
	@Override
	public String toString(){
		return "Request[FilePath = " + filePath + ", OutputPath = " + outputPath + "]";
	}
}
