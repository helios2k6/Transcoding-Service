package com.nlogneg.transcodingService.mediaInfo;

public abstract class MediaTrack extends Track{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8563336050612487821L;

	private String formatInfo;
	private String codecID;
	
	/**
	 * Get the format info
	 * @return The format info
	 */
	public String getFormatInfo(){
		return formatInfo;
	}
	
	/**
	 * Set the format info
	 * @param formatInfo The format info
	 */
	public void setFormatInfo(String formatInfo){
		this.formatInfo = formatInfo;
	}
	
	/**
	 * Get the codec ID
	 * @return The codec ID
	 */
	public String getCodecID(){
		return codecID;
	}
	
	/**
	 * Set the codec ID
	 * @param codecID The codec ID
	 */
	public void setCodecID(String codecID){
		this.codecID = codecID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codecID == null) ? 0 : codecID.hashCode());
		result = prime * result
				+ ((formatInfo == null) ? 0 : formatInfo.hashCode());
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
		MediaTrack other = (MediaTrack) obj;
		if (codecID == null){
			if (other.codecID != null){
				return false;
			}
		} else if (!codecID.equals(other.codecID)){
			return false;
		}
		if (formatInfo == null){
			if (other.formatInfo != null){
				return false;
			}
		} else if (!formatInfo.equals(other.formatInfo)){
			return false;
		}
		return true;
	}
}
