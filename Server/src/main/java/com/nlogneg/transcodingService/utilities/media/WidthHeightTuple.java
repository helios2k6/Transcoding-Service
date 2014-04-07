package com.nlogneg.transcodingService.utilities.media;

/**
 * Represents the width and height of a video
 * 
 * @author anjohnson
 * 
 */
public final class WidthHeightTuple
{
	private final int width;
	private final int height;

	/**
	 * @param width
	 * @param height
	 */
	public WidthHeightTuple(final int width, final int height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
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
		final WidthHeightTuple other = (WidthHeightTuple) obj;
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
}
