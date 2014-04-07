package com.nlogneg.transcodingService.info.mediainfo;

import java.util.List;

/**
 * Represents the File section of a MediaInfo XML file
 * 
 * @author anjohnson
 * 
 */
public final class File
{
	private final List<Track> tracks;

	/**
	 * Constructs a File object
	 * 
	 * @param tracks
	 *            The tracks of the File object
	 */
	public File(final List<Track> tracks)
	{
		this.tracks = tracks;
	}

	/**
	 * Get the tracks
	 * 
	 * @return The tracks
	 */
	public List<Track> getTracks()
	{
		return this.tracks;
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
		result = (prime * result) + ((this.tracks == null) ? 0 : this.tracks.hashCode());
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
		final File other = (File) obj;
		if (this.tracks == null)
		{
			if (other.tracks != null)
			{
				return false;
			}
		}
		else if (!this.tracks.equals(other.tracks))
		{
			return false;
		}
		return true;
	}

}
