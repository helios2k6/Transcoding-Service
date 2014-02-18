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
	public File getFile(){
		return file;
	}

	/**
	 * Set the file XML section
	 * @param file The file
	 */
	public void setFile(File file){
		this.file = file;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		MediaInfo other = (MediaInfo) obj;
		if (file == null){
			if (other.file != null){
				return false;
			}
		} else if (!file.equals(other.file)){
			return false;
		}
		return true;
	}
}
