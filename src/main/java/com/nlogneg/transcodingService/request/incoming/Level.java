package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the H.264 level parameter
 * 
 * @author anjohnson
 * 
 */
public final class Level implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3106477457601267041L;
	private final int major;
	private final int minor;

	/**
	 * Constructs a new level parameter
	 * 
	 * @param major
	 *            The major level
	 * @param minor
	 *            The minor level
	 */
	public Level(final int major, final int minor)
	{
		this.major = major;
		this.minor = minor;
	}

	/**
	 * Gets the major level
	 * 
	 * @return the major
	 */
	public int getMajor()
	{
		return this.major;
	}

	/**
	 * Gets the minor level
	 * 
	 * @return the minor
	 */
	public int getMinor()
	{
		return this.minor;
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
		result = (prime * result) + this.major;
		result = (prime * result) + this.minor;
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
		final Level other = (Level) obj;
		if (this.major != other.major)
		{
			return false;
		}
		if (this.minor != other.minor)
		{
			return false;
		}
		return true;
	}

	/**
	 * Converts the Level to arguments for x264
	 * 
	 * @param level
	 * @return
	 */
	public static List<String> convertToArguments(final Level level)
	{
		final StringBuilder builder = new StringBuilder();
		builder.append(level.getMajor()).append(".").append(level.getMinor());

		final List<String> arguments = new ArrayList<String>();

		arguments.add("--level");
		arguments.add(builder.toString());

		return arguments;
	}
}
