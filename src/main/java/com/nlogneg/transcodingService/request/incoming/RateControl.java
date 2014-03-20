package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the rate control setting for Encoding settings
 * 
 * @author anjohnson
 * 
 */
public class RateControl implements Serializable
{

	private static final String BitRateArgument = "--bitrate";
	private static final String ConstantBitRateArgument = "--crf";
	private static final String QPArgument = "--qp";

	/**
	 * 
	 */
	private static final long serialVersionUID = -3650899713022263523L;

	/**
	 * The type of rate control to use
	 * 
	 * @author anjohnson
	 * 
	 */
	public enum Type
	{
		ConstantRateFactor, QuantizationParameter, BitRate,
	}

	private final Type type;
	private final double setting;

	/**
	 * Constructs a new Rate Control object
	 * 
	 * @param type
	 *            The type of rate control
	 * @param setting
	 *            The setting for the rate control
	 */
	public RateControl(final Type type, final double setting)
	{
		this.type = type;
		this.setting = setting;
	}

	/**
	 * Get the type of rate control to use
	 * 
	 * @return The type of rate control
	 */
	public Type getType()
	{
		return this.type;
	}

	/**
	 * Get the setting for the rate control
	 * 
	 * @return The setting
	 */
	public double getSetting()
	{
		return this.setting;
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
		temp = Double.doubleToLongBits(this.setting);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		result = (prime * result)
				+ ((this.type == null) ? 0 : this.type.hashCode());
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
		final RateControl other = (RateControl) obj;
		if (Double.doubleToLongBits(this.setting) != Double
				.doubleToLongBits(other.setting))
		{
			return false;
		}
		if (this.type != other.type)
		{
			return false;
		}
		return true;
	}

	public static List<String> convertToArguments(final RateControl control)
	{
		final List<String> arguments = new LinkedList<String>();

		switch (control.getType())
		{
		case BitRate:
			arguments.add(BitRateArgument);
			break;
		case ConstantRateFactor:
			arguments.add(ConstantBitRateArgument);
			break;
		case QuantizationParameter:
			arguments.add(QPArgument);
			arguments.add(Integer.toString((int) control.getSetting()));
			return arguments;
		}

		arguments.add(Double.toString(control.getSetting()));
		return arguments;
	}
}
