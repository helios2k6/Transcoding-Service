package com.nlogneg.transcodingService.info.mediainfo;

/**
 * Represents a subtitle track
 * 
 * @author anjohnson
 * 
 */
public final class TextTrack extends MediaTrack
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4632359334810139562L;

	private final String language;

	/**
	 * Construct a TextTrack
	 * 
	 * @param format
	 *            The format
	 * @param codecID
	 *            The codec ID
	 * @param id
	 *            The track id
	 * @param language
	 *            The language of the track
	 */
	public TextTrack(
			final String format,
			final String codecID,
			final int id,
			final String language)
	{
		super(format, codecID, id);
		this.language = language;
	}

	/**
	 * @return the language
	 */
	public String getLanguage()
	{
		return this.language;
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
		result = (prime * result)
				+ ((this.language == null) ? 0 : this.language.hashCode());
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
		final TextTrack other = (TextTrack) obj;
		if (this.language == null)
		{
			if (other.language != null)
			{
				return false;
			}
		} else if (!this.language.equals(other.language))
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
