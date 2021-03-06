package com.nlogneg.transcodingService.info.mediainfo;

import java.util.List;

/**
 * A factory class that generates media info track summaries
 * 
 * @author anjohnson
 * 
 */
public final class MediaInfoTrackSummaryFactory
{

	/**
	 * Get the media info track summary for the given media info
	 * 
	 * @param mediaInfo
	 *            The media info
	 * @return The media info track summary
	 */
	public static MediaInfoTrackSummary getSummary(final MediaInfo mediaInfo)
	{
		if (mediaInfo == null)
		{
			throw new NullPointerException();
		}

		final List<Track> tracks = mediaInfo.getFile().getTracks();
		final MediaInfoTrackSummary summary = new MediaInfoTrackSummary();
		for (final Track track : tracks)
		{
			track.accept(summary);
		}

		return summary;
	}
}
