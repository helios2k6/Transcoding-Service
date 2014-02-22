package com.nlogneg.transcodingService.requests;


/**
 * Represents a transcoding request. 
 * @author anjohnson
 *
 */
public class Request{
	
	private final String sourceFile;
	private final String destinationFile;
	private final EncodingSettings encodingSettings;
	private final Selector selector;

	/**
	 * Constructs a new, immutable request
	 * @param filePath The file path
	 * @param outputPath The output path
	 * @param settings The encoding settings for this request
	 * @param selector The selector
	 */
	protected Request(String filePath, String outputPath, EncodingSettings settings, Selector selector){
		this.sourceFile = filePath;
		this.destinationFile = outputPath;
		this.encodingSettings = settings;
		this.selector = selector;
	}
	
	/**
	 * @return the selector
	 */
	public Selector getSelector() {
		return selector;
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
	public EncodingSettings getEncodingSettings(){
		return encodingSettings;
	}
	
	@Override
	public String toString(){
		return "Request[FilePath = " + sourceFile + ", OutputPath = " + destinationFile + "]";
	}
}
