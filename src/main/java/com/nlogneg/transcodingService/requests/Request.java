package com.nlogneg.transcodingService.requests;

/**
 * Represents a transcoding request. 
 * @author anjohnson
 *
 */
public abstract class Request{
	public enum OutputType{
		STANDARD_OUTPUT,
		FILE,
		SOCKET
	}
	
	private final String filePath;
	private final String outputPath;
	private final OutputType outputType;

	/**
	 * Constructs a new, immutable request
	 * @param filePath The file path
	 * @param outputPath The output path
	 * @param outputType The output type
	 */
	protected Request(String filePath, String outputPath, OutputType outputType){
		this.filePath = filePath;
		this.outputPath = outputPath;
		this.outputType = outputType;
	}
	
	public OutputType getOutputType(){
		return outputType;
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
	
	@Override
	public String toString(){
		return "Request[FilePath = " + filePath + ", OutputPath = " + outputPath + "]";
	}
}
