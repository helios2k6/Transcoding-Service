package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class SampleAspectRatio implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3835516724765000925L;

	private final int width;
	private final int height;

	public SampleAspectRatio(final int width, final int height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * Get the width
	 * 
	 * @return the width
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * Get the height
	 * 
	 * @return the height
	 */
	public int getHeight()
	{
		return this.height;
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
		result = (prime * result) + this.height;
		result = (prime * result) + this.width;
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
		final SampleAspectRatio other = (SampleAspectRatio) obj;
		if (this.height != other.height)
		{
			return false;
		}
		if (this.width != other.width)
		{
			return false;
		}
		return true;
	}

	/**
	 * Converts a SampleAspectRatio to a list of arguments for x264
	 * 
	 * @param ratio
	 * @return
	 */
	public static List<String> convertToArguments(final SampleAspectRatio ratio)
	{
		final StringBuilder builder = new StringBuilder();
		builder.append(ratio.getWidth()).append(":").append(ratio.getHeight());

		final List<String> arguments = new ArrayList<String>();
		arguments.add("--sar");
		arguments.add(builder.toString());

		return arguments;
	}
}
