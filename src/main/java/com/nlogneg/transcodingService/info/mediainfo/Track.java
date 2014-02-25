package com.nlogneg.transcodingService.info.mediainfo;

import java.io.Serializable;

/**
 * Represents the Track XML section of the MediaInfo XML file
 * @author anjohnson
 *
 */
public abstract class Track implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5913948153707934965L;
	
	private final String format;

	/**
	 * Creates a new track object
	 * @param format
	 */
	public Track(String format) {
		this.format = format;
	}

	/**
	 * Gets the format
	 * @return The format
	 */
	public String getFormat(){
		return format;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((format == null) ? 0 : format.hashCode());
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
		Track other = (Track) obj;
		if (format == null){
			if (other.format != null){
				return false;
			}
		} else if (!format.equals(other.format)){
			return false;
		}
		return true;
	}
	
	/**
	 * Accepts a TrackVisitor
	 * @param visitor The visitor
	 */
	public void accept(TrackVisitor visitor){
		visitor.visit(this);
	}
}
