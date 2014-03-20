package com.nlogneg.transcodingService.info.mediainfo;

/**
 * Represents an audio track in a mediainfo xml file
 * @author anjohnson
 *
 */
public final class AudioTrack extends MediaTrack{

	/**
	 * 
	 */
	private static final long serialVersionUID = -327107175740055297L;

	private final String channels;
	private final String language;
	
	/**
	 * Constructs a new audio track
	 * @param format The format
	 * @param codecID The codec ID
	 * @param id The track ID
	 * @param channels The number of audio channels
	 * @param language The language
	 */
	public AudioTrack(
			String format, 
			String codecID,
			int id,
			String channels,
			String language){
		super(format, codecID, id);
		this.channels = channels;
		this.language = language;
	}

	/**
	 * Get the number of channels
	 * @return The number of channels
	 */
	public String getChannels(){
		return channels;
	}
	
	/**
	 * Get the language
	 * @return The language
	 */
	public String getLanguage(){
		return language;
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
	
	public void accept(TrackVisitor visitor){
		visitor.visit(this);
	}
}
