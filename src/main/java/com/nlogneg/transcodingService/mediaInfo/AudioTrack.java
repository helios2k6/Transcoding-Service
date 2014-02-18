package com.nlogneg.transcodingService.mediaInfo;

/**
 * Represents an audio track in a mediainfo xml file
 * @author anjohnson
 *
 */
public class AudioTrack extends MediaTrack{

	/**
	 * 
	 */
	private static final long serialVersionUID = -327107175740055297L;

	private String channels;
	private String language;
	
	/**
	 * Get the number of channels
	 * @return The number of channels
	 */
	public String getChannels(){
		return channels;
	}
	
	/**
	 * Set the number of channels
	 * @param channels The number of channels
	 */
	public void setChannels(String channels){
		this.channels = channels;
	}
	
	/**
	 * Get the language
	 * @return The language
	 */
	public String getLanguage(){
		return language;
	}
	
	/**
	 * Set the language
	 * @param language The language
	 */
	public void setLanguage(String language){
		this.language = language;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((channels == null) ? 0 : channels.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
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
		if (!super.equals(obj)){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		AudioTrack other = (AudioTrack) obj;
		if (channels == null){
			if (other.channels != null){
				return false;
			}
		} else if (!channels.equals(other.channels)){
			return false;
		}
		if (language == null){
			if (other.language != null){
				return false;
			}
		} else if (!language.equals(other.language)){
			return false;
		}
		return true;
	}
}
