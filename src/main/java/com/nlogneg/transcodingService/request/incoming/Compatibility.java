package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.List;

/**
 * Constructs a new Compatibility object
 * 
 * @author anjohnson
 * 
 */
public final class Compatibility implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6880522410686420605L;

	private final SampleAspectRatio sampleAspectRatio;
	private final boolean useAccessUnitDelimiters;

	/**
	 * Construct a new compatibility object
	 * 
	 * @param sampleAspectRatio
	 *            The sample aspect ratio
	 * @param useAccessUnitDelimiters
	 *            Whether to use access unit delimiters
	 */
	public Compatibility(
			final SampleAspectRatio sampleAspectRatio,
			final boolean useAccessUnitDelimiters)
	{
		this.sampleAspectRatio = sampleAspectRatio;
		this.useAccessUnitDelimiters = useAccessUnitDelimiters;
	}

	/**
	 * @return the sampleAspectRatio
	 */
	public SampleAspectRatio getSampleAspectRatio()
	{
		return this.sampleAspectRatio;
	}

	/**
	 * @return the useAccessUnitDelimiters
	 */
	public boolean useAccessUnitDelimiters()
	{
		return this.useAccessUnitDelimiters;
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
				+ ((this.sampleAspectRatio == null) ? 0
						: this.sampleAspectRatio.hashCode());
		result = (prime * result)
				+ (this.useAccessUnitDelimiters ? 1231 : 1237);
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
		final Compatibility other = (Compatibility) obj;
		if (this.sampleAspectRatio == null)
		{
			if (other.sampleAspectRatio != null)
			{
				return false;
			}
		} else if (!this.sampleAspectRatio.equals(other.sampleAspectRatio))
		{
			return false;
		}
		if (this.useAccessUnitDelimiters != other.useAccessUnitDelimiters)
		{
			return false;
		}
		return true;
	}

	/**
	 * Converts a Compatibility to an argument list for x264
	 * 
	 * @param compatibility
	 * @return
	 */
	public static List<String> convertToArguments(
			final Compatibility compatibility)
	{
		final List<String> arguments = SampleAspectRatio
				.convertToArguments(compatibility.getSampleAspectRatio());

		if (compatibility.useAccessUnitDelimiters())
		{
			arguments.add("--aud");
		}

		return arguments;
	}
}
