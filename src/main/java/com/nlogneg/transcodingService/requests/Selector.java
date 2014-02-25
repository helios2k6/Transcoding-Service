package com.nlogneg.transcodingService.requests;

import java.io.Serializable;

/**
 * Represents the media file selector overrides for a particular request
 * @author Andrew
 *
 */
public final class Selector implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5736826441729831051L;
	
	private final boolean force169AspectRatio;
	private final boolean forceUseAudioTrack;
	private final boolean forceResolution;
	
	private final long audioTrack;
	private final long forceHeight;
	private final long forceWidth;
	
	/**
	 * @param force169AspectRatio
	 * @param forceUseAudioTrack
	 * @param forceResolution
	 * @param audioTrack
	 * @param forceHeight
	 * @param forceWidth
	 */
	public Selector(
			boolean force169AspectRatio, 
			boolean forceUseAudioTrack,
			boolean forceResolution, 
			long audioTrack, 
			long forceHeight,
			long forceWidth){
		
		this.force169AspectRatio = force169AspectRatio;
		this.forceUseAudioTrack = forceUseAudioTrack;
		this.forceResolution = forceResolution;
		this.audioTrack = audioTrack;
		this.forceHeight = forceHeight;
		this.forceWidth = forceWidth;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the force169AspectRatio
	 */
	public boolean isForce169AspectRatio() {
		return force169AspectRatio;
	}

	/**
	 * @return the forceUseAudioTrack
	 */
	public boolean isForceUseAudioTrack() {
		return forceUseAudioTrack;
	}

	/**
	 * @return the forceResolution
	 */
	public boolean isForceResolution() {
		return forceResolution;
	}

	/**
	 * @return the audioTrack
	 */
	public long getAudioTrack() {
		return audioTrack;
	}

	/**
	 * @return the forceHeight
	 */
	public long getForceHeight() {
		return forceHeight;
	}

	/**
	 * @return the forceWidth
	 */
	public long getForceWidth() {
		return forceWidth;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (audioTrack ^ (audioTrack >>> 32));
		result = prime * result + (force169AspectRatio ? 1231 : 1237);
		result = prime * result + (int) (forceHeight ^ (forceHeight >>> 32));
		result = prime * result + (forceResolution ? 1231 : 1237);
		result = prime * result + (forceUseAudioTrack ? 1231 : 1237);
		result = prime * result + (int) (forceWidth ^ (forceWidth >>> 32));
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
		Selector other = (Selector) obj;
		if (audioTrack != other.audioTrack) {
			return false;
		}
		if (force169AspectRatio != other.force169AspectRatio) {
			return false;
		}
		if (forceHeight != other.forceHeight) {
			return false;
		}
		if (forceResolution != other.forceResolution) {
			return false;
		}
		if (forceUseAudioTrack != other.forceUseAudioTrack) {
			return false;
		}
		if (forceWidth != other.forceWidth) {
			return false;
		}
		return true;
	}
}
