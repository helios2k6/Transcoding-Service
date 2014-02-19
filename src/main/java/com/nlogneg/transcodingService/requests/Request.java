package com.nlogneg.transcodingService.requests;

import com.nlogneg.transcodingService.media.EncodingSettings;

/**
 * Represents a transcoding request. 
 * @author anjohnson
 *
 */
public class Request{
	
	private final String sourceFile;
	private final String destinationFile;
	private final EncodingSettings encodingSettings;

	/**
	 * Constructs a new, immutable request
	 * @param filePath The file path
	 * @param outputPath The output path
	 * @param settings The encoding settings for this request
	 */
	protected Request(String filePath, String outputPath, EncodingSettings settings){
		this.sourceFile = filePath;
		this.destinationFile = outputPath;
		this.encodingSettings = settings;
	}
	
	/**
	 * Get the file path
	 * @return
	 */
	public String getSourceFile(){
		return sourceFile;
	}

	/**
	 * Get the output path
	 * @return
	 */
	public String getDestinationFile(){
		return destinationFile;
	}
	
	/**
	 * Gets the encoding settings for this request
	 * @return The encoding settings
	 */
	public EncodingSettings getSettings(){
		return encodingSettings;
	}
	
	@Override
	public String toString(){
		return "Request[FilePath = " + sourceFile + ", OutputPath = " + destinationFile + "]";
	}
}
