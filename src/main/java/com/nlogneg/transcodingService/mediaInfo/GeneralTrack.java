package com.nlogneg.transcodingService.mediaInfo;

/**
 * Represents the General type track
 * @author anjohnson
 *
 */
public final class GeneralTrack extends Track{

	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -1656903100542171365L;
	
	private String completeName;

	/**
	 * Gets the complete name of the file
	 * @return The complete name of the file
	 */
	public String getCompleteName(){
		return completeName;
	}

	/**
	 * Sets the complete name of the file
	 * @param completeName The complete name of the file
	 */
	public void setCompleteName(String completeName){
		this.completeName = completeName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((completeName == null) ? 0 : completeName.hashCode());
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
		GeneralTrack other = (GeneralTrack) obj;
		if (completeName == null){
			if (other.completeName != null){
				return false;
			}
		} else if (!completeName.equals(other.completeName)){
			return false;
		}
		return true;
	}
}
