package com.nlogneg.transcodingService.media;

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
	
	
}
