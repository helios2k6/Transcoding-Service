package com.nlogneg.transcodingService.info.mediainfo;

import java.util.Collection;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A set of media track utilities
 * 
 * @author anjohnson
 * 
 */
public final class MediaTrackUtils
{

	/**
	 * Get a media track by ID
	 * 
	 * @param id
	 * @return
	 */
	public static <T extends MediaTrack> Optional<T> tryGetMediaTrackById(
			final Collection<T> tracks,
			final int id)
	{
		for (final T t : tracks)
		{
			if (t.getId() == id)
			{
				return Optional.make(t);
			}
		}

		return Optional.none();
	}

}
