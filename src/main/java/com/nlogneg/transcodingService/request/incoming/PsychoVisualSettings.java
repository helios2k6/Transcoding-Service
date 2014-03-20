package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of the psychoVisual rate-distortion setting
 * 
 * @author anjohnson
 * 
 */
public final class PsychoVisualSettings implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7133538179699030793L;
	private final double alpha;
	private final double beta;

	/**
	 * Constructs a new Psychovisual Settings object
	 * 
	 * @param alpha
	 *            The alpha value
	 * @param beta
	 *            The beta value
	 */
	public PsychoVisualSettings(final double alpha, final double beta)
	{
		this.alpha = alpha;
		this.beta = beta;
	}

	/**
	 * @return the alpha
	 */
	public double getAlpha()
	{
		return this.alpha;
	}

	/**
	 * @return the beta
	 */
	public double getBeta()
	{
		return this.beta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.alpha);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.beta);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final PsychoVisualSettings other = (PsychoVisualSettings) obj;
		if (Double.doubleToLongBits(this.alpha) != Double
				.doubleToLongBits(other.alpha))
		{
			return false;
		}
		if (Double.doubleToLongBits(this.beta) != Double
				.doubleToLongBits(other.beta))
		{
			return false;
		}
		return true;
	}

	/**
	 * Convert PsychoVisualSettings to arguments for x264
	 * 
	 * @param settings
	 * @return
	 */
	public static List<String> convertToArguments(
			final PsychoVisualSettings settings)
	{
		final StringBuilder builder = new StringBuilder();
		builder.append(settings.getAlpha()).append(":")
				.append(settings.getBeta());

		final List<String> arguments = new ArrayList<String>();

		arguments.add("--psy-rd");
		arguments.add(builder.toString());

		return arguments;
	}
}
