package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the Estimation settings to use for encoding
 * 
 * @author anjohnson
 * 
 */
public final class Estimation implements Serializable
{

	private static final String MotionEstimationArgument = "--me";
	private static final String TrellisArgument = "--trellis";
	private static final String SubpixelMotionEstimation = "--subme";

	private static final String DiamondArgument = "dia";
	private static final String HexagonArgument = "hex";
	private static final String UnevenMultiHexArgument = "umh";
	private static final String ExhaustiveArgument = "exa";
	private static final String TransformExhaustive = "tesa";

	/**
	 * 
	 */
	private static final long serialVersionUID = 3242718763086459275L;

	/**
	 * Represents what technique to use for motion estimation
	 * 
	 * @author anjohnson
	 * 
	 */
	public enum MotionEstimation implements Serializable
	{
		Diamond, Hexagon, UnevenMultiHexagon, Exhaustive, TransformExhaustive
	}

	private final MotionEstimation motionEstimation;
	private final int trellis;
	private final int subpixelMotionEstimation;

	/**
	 * Constructs a new Estimation object
	 * 
	 * @param motionEstimation
	 *            The motion estimation to use
	 * @param trellis
	 *            Which trellis level to use
	 * @param subpixelMotionEstimation
	 *            What subpixel motion estimation level to use
	 */
	public Estimation(
			final MotionEstimation motionEstimation,
			final int trellis,
			final int subpixelMotionEstimation)
	{
		this.motionEstimation = motionEstimation;
		this.trellis = trellis;
		this.subpixelMotionEstimation = subpixelMotionEstimation;
	}

	/**
	 * Get the motion estimation method
	 * 
	 * @return The motion estimation
	 */
	public MotionEstimation getMotionEstimation()
	{
		return this.motionEstimation;
	}

	/**
	 * Get the trellis level
	 * 
	 * @return The trellis level
	 */
	public int getTrellis()
	{
		return this.trellis;
	}

	/**
	 * Get the subpixel motion estimation level
	 * 
	 * @return The subpixel motion estimation level
	 */
	public int getSubpixelMotionEstimation()
	{
		return this.subpixelMotionEstimation;
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
		result = (prime * result)
				+ ((this.motionEstimation == null) ? 0 : this.motionEstimation
						.hashCode());
		result = (prime * result) + this.subpixelMotionEstimation;
		result = (prime * result) + this.trellis;
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
		final Estimation other = (Estimation) obj;
		if (this.motionEstimation != other.motionEstimation)
		{
			return false;
		}
		if (this.subpixelMotionEstimation != other.subpixelMotionEstimation)
		{
			return false;
		}
		if (this.trellis != other.trellis)
		{
			return false;
		}
		return true;
	}

	public static List<String> convertToArguments(final Estimation estimation)
	{
		final List<String> arguments = new LinkedList<String>();
		arguments.add(MotionEstimationArgument);

		switch (estimation.getMotionEstimation())
		{
		case Diamond:
			arguments.add(DiamondArgument);
			break;
		case Exhaustive:
			arguments.add(ExhaustiveArgument);
			break;
		case Hexagon:
			arguments.add(HexagonArgument);
			break;
		case TransformExhaustive:
			arguments.add(TransformExhaustive);
			break;
		case UnevenMultiHexagon:
			arguments.add(UnevenMultiHexArgument);
			break;
		}

		arguments.add(TrellisArgument);
		arguments.add(Integer.toString(estimation.getTrellis()));

		arguments.add(SubpixelMotionEstimation);
		arguments
				.add(Integer.toString(estimation.getSubpixelMotionEstimation()));

		return arguments;
	}
}
