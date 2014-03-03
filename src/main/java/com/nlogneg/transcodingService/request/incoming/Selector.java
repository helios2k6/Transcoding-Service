package com.nlogneg.transcodingService.request.incoming;

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
	private final boolean capResolution;
	
	private final long audioTrack;
	private final long maxHeight;
	private final long maxWidth;
	
	/**
	 * @param force169AspectRatio
	 * @param forceUseAudioTrack
	 * @param capResolution
	 * @param audioTrack
	 * @param maxHeight
	 * @param maxWidth
	 */
	public Selector(
			boolean force169AspectRatio, 
			boolean forceUseAudioTrack,
			boolean capResolution, 
			long audioTrack, 
			long maxHeight,
			long maxWidth){
		
		this.force169AspectRatio = force169AspectRatio;
		this.forceUseAudioTrack = forceUseAudioTrack;
		this.capResolution = capResolution;
		this.audioTrack = audioTrack;
		this.maxHeight = maxHeight;
		this.maxWidth = maxWidth;
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
	 * @return the capResolution
	 */
	public boolean isCapResolution() {
		return capResolution;
	}

	/**
	 * @return the audioTrack
	 */
	public long getAudioTrack() {
		return audioTrack;
	}

	/**
	 * @return the maxHeight
	 */
	public long getMaxHeight() {
		return maxHeight;
	}

	/**
	 * @return the maxWidth
	 */
	public long getMaxWidth() {
		return maxWidth;
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
		result = prime * result + (int) (maxHeight ^ (maxHeight >>> 32));
		result = prime * result + (capResolution ? 1231 : 1237);
		result = prime * result + (forceUseAudioTrack ? 1231 : 1237);
		result = prime * result + (int) (maxWidth ^ (maxWidth >>> 32));
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
		if (maxHeight != other.maxHeight) {
			return false;
		}
		if (capResolution != other.capResolution) {
			return false;
		}
		if (forceUseAudioTrack != other.forceUseAudioTrack) {
			return false;
		}
		if (maxWidth != other.maxWidth) {
			return false;
		}
		return true;
	}
}
