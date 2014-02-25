package com.nlogneg.transcodingService.request.incoming;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(alpha);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(beta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		PsychoVisualSettings other = (PsychoVisualSettings) obj;
		if (Double.doubleToLongBits(alpha) != Double
				.doubleToLongBits(other.alpha)) {
			return false;
		}
		if (Double.doubleToLongBits(beta) != Double
				.doubleToLongBits(other.beta)) {
			return false;
		}
		return true;
	}
	
	
}
