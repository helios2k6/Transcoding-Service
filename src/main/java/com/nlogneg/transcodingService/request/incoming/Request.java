package com.nlogneg.transcodingService.request.incoming;



/**
 * Represents a transcoding request. 
 * @author anjohnson
 *
 */
public final class Request{
	
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
	public Selector getSelector(){
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destinationFile == null) ? 0 : destinationFile.hashCode());
		result = prime
				* result
				+ ((encodingSettings == null) ? 0 : encodingSettings.hashCode());
		result = prime * result
				+ ((selector == null) ? 0 : selector.hashCode());
		result = prime * result
				+ ((sourceFile == null) ? 0 : sourceFile.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Request other = (Request) obj;
		if (destinationFile == null) {
			if (other.destinationFile != null) {
				return false;
			}
		} else if (!destinationFile.equals(other.destinationFile)) {
			return false;
		}
		if (encodingSettings == null) {
			if (other.encodingSettings != null) {
				return false;
			}
		} else if (!encodingSettings.equals(other.encodingSettings)) {
			return false;
		}
		if (selector == null) {
			if (other.selector != null) {
				return false;
			}
		} else if (!selector.equals(other.selector)) {
			return false;
		}
		if (sourceFile == null) {
			if (other.sourceFile != null) {
				return false;
			}
		} else if (!sourceFile.equals(other.sourceFile)) {
			return false;
		}
		return true;
	}

}
