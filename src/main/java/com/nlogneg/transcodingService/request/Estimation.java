package com.nlogneg.transcodingService.request;

import java.io.Serializable;

/**
 * Represents the Estimation settings to use for encoding
 * @author anjohnson
 *
 */
public final class Estimation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3242718763086459275L;

	/**
	 * Represents what technique to use for motion estimation
	 * @author anjohnson
	 *
	 */
	public enum MotionEstimation implements Serializable{
		Diamond,
		Hexagon,
		UnevenMultiHexagon,
		Exhaustive,
		TransformExhaustive
	}
	
	private final MotionEstimation motionEstimation;
	private final int trellis;
	private final int subpixelMotionEstimation;
	
	/**
	 * Constructs a new Estimation object
	 * @param motionEstimation The motion estimation to use
	 * @param trellis Which trellis level to use
	 * @param subpixelMotionEstimation What subpixel motion estimation level to use
	 */
	public Estimation(MotionEstimation motionEstimation, int trellis, int subpixelMotionEstimation){
		this.motionEstimation = motionEstimation;
		this.trellis = trellis;
		this.subpixelMotionEstimation = subpixelMotionEstimation;
	}

	/**
	 * Get the motion estimation method
	 * @return The motion estimation
	 */
	public MotionEstimation getMotionEstimation() {
		return motionEstimation;
	}

	/**
	 * Get the trellis level
	 * @return The trellis level
	 */
	public int getTrellis() {
		return trellis;
	}

	/**
	 * Get the subpixel motion estimation level
	 * @return The subpixel motion estimation level
	 */
	public int getSubpixelMotionEstimation() {
		return subpixelMotionEstimation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((motionEstimation == null) ? 0 : motionEstimation.hashCode());
		result = prime * result + subpixelMotionEstimation;
		result = prime * result + trellis;
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
		Estimation other = (Estimation) obj;
		if (motionEstimation != other.motionEstimation) {
			return false;
		}
		if (subpixelMotionEstimation != other.subpixelMotionEstimation) {
			return false;
		}
		if (trellis != other.trellis) {
			return false;
		}
		return true;
	}
	
	
}
