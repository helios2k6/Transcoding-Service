package com.nlogneg.transcodingService.info.mediainfo;

public abstract class MediaTrack extends Track
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8563336050612487821L;

	private final String codecID;
	private final int id;

	/**
	 * Creates a new media track
	 * 
	 * @param format
	 *            The format
	 * @param formatInfo
	 *            the format info
	 * @param codecID
	 *            The codec ID
	 * @param id
	 *            The track ID
	 */
	public MediaTrack(final String format, final String codecID, final int id)
	{
		super(format);
		this.codecID = codecID;
		this.id = id;
	}

	/**
	 * Get the codec ID
	 * 
	 * @return The codec ID
	 */
	public String getCodecID()
	{
		return this.codecID;
	}

	/**
	 * Get the track ID
	 * 
	 * @return The track ID
	 */
	public int getId()
	{
		return this.id;
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
		result = (prime * result) + ((this.codecID == null) ? 0 : this.codecID.hashCode());
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
		final MediaTrack other = (MediaTrack) obj;
		if (this.codecID == null)
		{
			if (other.codecID != null)
			{
				return false;
			}
		}
		else if (!this.codecID.equals(other.codecID))
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
