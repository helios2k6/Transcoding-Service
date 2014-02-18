package com.nlogneg.transcodingService.mediaInfo;

/**
 * Represents the XML passed from MediaInfo
 * @author anjohnson
 *
 */
public class MediaInfo{
	private File file;

	/**
	 * Get the file XML section
	 * @return The file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Set the file XML section
	 * @param file The file
	 */
	public void setFile(File file) {
		this.file = file;
	}
}
