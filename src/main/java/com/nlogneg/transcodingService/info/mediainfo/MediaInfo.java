package com.nlogneg.transcodingService.info.mediainfo;

/**
 * Represents the XML passed from MediaInfo
 * 
 * @author anjohnson
 * 
 */
public final class MediaInfo
{
	private final File file;

	/**
	 * Constructs a new media info object
	 * 
	 * @param file
	 *            The file XML section
	 */
	public MediaInfo(final File file)
	{
		this.file = file;
	}

	/**
	 * Get the file XML section
	 * 
	 * @return The file
	 */
	public File getFile()
	{
		return this.file;
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
		result = (prime * result) + ((this.file == null) ? 0 : this.file.hashCode());
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
		final MediaInfo other = (MediaInfo) obj;
		if (this.file == null)
		{
			if (other.file != null)
			{
				return false;
			}
		} else if (!this.file.equals(other.file))
		{
			return false;
		}
		return true;
	}
}
