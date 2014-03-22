package com.nlogneg.transcodingService.info.mediainfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Audio track specific utility functions
 * 
 * @author anjohnson
 * 
 */
public final class AudioTrackUtils
{
	private static final Logger Log = LogManager.getLogger(AudioTrackUtils.class);

	/**
	 * Attempts to get the number of channels for a given
	 * 
	 * @param track
	 * @return
	 */
	public static int getNumberOfChannels(final AudioTrack track)
	{
		final String channels = track.getChannels();
		final String[] split = channels.split(" ");
		if (split.length > 1)
		{
			try
			{
				return Integer.parseInt(split[0]);
			} catch (final NumberFormatException e)
			{
				Log.error(
						"Could not parse number of channels for audio track.",
						e);
			}
		}
		return -1;
	}
}
