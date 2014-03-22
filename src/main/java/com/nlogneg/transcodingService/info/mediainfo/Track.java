package com.nlogneg.transcodingService.info.mediainfo;

import java.io.Serializable;

/**
 * Represents the Track XML section of the MediaInfo XML file
 * 
 * @author anjohnson
 * 
 */
public abstract class Track implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5913948153707934965L;

	private final String format;

	/**
	 * Creates a new track object
	 * 
	 * @param format
	 */
	public Track(final String format)
	{
		this.format = format;
	}

	/**
	 * Gets the format
	 * 
	 * @return The format
	 */
	public String getFormat()
	{
		return this.format;
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
		result = (prime * result) + ((this.format == null) ? 0 : this.format.hashCode());
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
		final Track other = (Track) obj;
		if (this.format == null)
		{
			if (other.format != null)
			{
				return false;
			}
		} else if (!this.format.equals(other.format))
		{
			return false;
		}
		return true;
	}

	/**
	 * Accepts a TrackVisitor
	 * 
	 * @param visitor
	 *            The visitor
	 */
	public void accept(final TrackVisitor visitor)
	{
		visitor.visit(this);
	}
}
