package com.nlogneg.transcodingService.media;

import java.io.Serializable;

/**
 * A representation of the psychoVisual rate-distortion setting
 * @author anjohnson
 *
 */
public final class PsychoVisualSettings implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7133538179699030793L;
	private final double alpha;
	private final double beta;
	
	/**
	 * Constructs a new Psychovisual Settings object
	 * @param alpha The alpha value
	 * @param beta The beta value
	 */
	public PsychoVisualSettings(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
	}

	/**
	 * @return the alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * @return the beta
	 */
	public double getBeta() {
		return beta;
	}
	
	
}
