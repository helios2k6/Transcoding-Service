package com.nlogneg.transcodingService.mediaInfo;

/**
 * Represents a subtitle track
 * @author anjohnson
 *
 */
public class TextTrack extends MediaTrack{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4632359334810139562L;
	
	private final String language;

	/**
	 * Construct a TextTrack
	 * @param format The format
	 * @param codecID The codec ID
	 * @param id The track id
	 * @param language The language of the track
	 */
	public TextTrack(String format, String codecID, int id, String language) {
		super(format, codecID, id);
		this.language = language;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TextTrack other = (TextTrack) obj;
		if (language == null) {
			if (other.language != null) {
				return false;
			}
		} else if (!language.equals(other.language)) {
			return false;
		}
		return true;
	}
	
	public void accept(TrackVisitor visitor){
		visitor.visit(this);
	}
}
