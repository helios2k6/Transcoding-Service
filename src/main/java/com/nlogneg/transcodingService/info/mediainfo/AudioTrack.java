package com.nlogneg.transcodingService.info.mediainfo;

/**
 * Represents an audio track in a mediainfo xml file
 * 
 * @author anjohnson
 * 
 */
public final class AudioTrack extends MediaTrack
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -327107175740055297L;

	private final String channels;
	private final String language;

	/**
	 * Constructs a new audio track
	 * 
	 * @param format
	 *            The format
	 * @param codecID
	 *            The codec ID
	 * @param id
	 *            The track ID
	 * @param channels
	 *            The number of audio channels
	 * @param language
	 *            The language
	 */
	public AudioTrack(
			final String format,
			final String codecID,
			final int id,
			final String channels,
			final String language)
	{
		super(format, codecID, id);
		this.channels = channels;
		this.language = language;
	}

	/**
	 * Get the number of channels
	 * 
	 * @return The number of channels
	 */
	public String getChannels()
	{
		return this.channels;
	}

	/**
	 * Get the language
	 * 
	 * @return The language
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
		result = (prime * result) + ((this.channels == null) ? 0 : this.channels.hashCode());
		result = (prime * result) + ((this.language == null) ? 0 : this.language.hashCode());
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
		final AudioTrack other = (AudioTrack) obj;
		if (this.channels == null)
		{
			if (other.channels != null)
			{
				return false;
			}
		}
		else if (!this.channels.equals(other.channels))
		{
			return false;
		}
		if (this.language == null)
		{
			if (other.language != null)
			{
				return false;
			}
		}
		else if (!this.language.equals(other.language))
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
