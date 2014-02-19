package com.nlogneg.transcodingService.mediaInfo;

public abstract class MediaTrack extends Track{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8563336050612487821L;

	private final String codecID;
	
	/**
	 * Creates a new media track
	 * @param format The format
	 * @param formatInfo the format info
	 * @param codecID The 
	 */
	public MediaTrack(String format, String codecID) {
		super(format);
		this.codecID = codecID;
	}

	/**
	 * Get the codec ID
	 * @return The codec ID
	 */
	public String getCodecID(){
		return codecID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codecID == null) ? 0 : codecID.hashCode());
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
		MediaTrack other = (MediaTrack) obj;
		if (codecID == null) {
			if (other.codecID != null) {
				return false;
			}
		} else if (!codecID.equals(other.codecID)) {
			return false;
		}
		return true;
	}
	
	public void accept(TrackVisitor visitor){
		visitor.visit(this);
	}
}
