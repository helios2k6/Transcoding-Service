package com.nlogneg.transcodingService.info.mediainfo;

/**
 * Represents the General type track
 * 
 * @author anjohnson
 * 
 */
public final class GeneralTrack extends Track
{

	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -1656903100542171365L;

	private final String completeName;

	/**
	 * Creates a new General Track
	 * 
	 * @param format
	 *            The format
	 * @param completeName
	 *            The complete name
	 */
	public GeneralTrack(final String format, final String completeName)
	{
		super(format);
		this.completeName = completeName;
	}

	/**
	 * Gets the complete name of the file
	 * 
	 * @return The complete name of the file
	 */
	public String getCompleteName()
	{
		return this.completeName;
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
		int result = super.hashCode();
		result = (prime * result) + ((this.completeName == null) ? 0 : this.completeName.hashCode());
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
		if (!super.equals(obj))
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final GeneralTrack other = (GeneralTrack) obj;
		if (this.completeName == null)
		{
			if (other.completeName != null)
			{
				return false;
			}
		} else if (!this.completeName.equals(other.completeName))
		{
			return false;
		}
		return true;
	}

	@Override
	public void accept(final TrackVisitor visitor)
	{
		visitor.visit(this);
	}
}
