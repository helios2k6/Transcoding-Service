package com.nlogneg.transcodingService.encoding;

import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummaryFactory;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.VideoTrack;
import com.nlogneg.transcodingService.info.mediainfo.VideoTrackUtils;
import com.nlogneg.transcodingService.request.incoming.Selector;
import com.nlogneg.transcodingService.utilities.math.IntegerUtils;
import com.nlogneg.transcodingService.utilities.media.WidthHeightTuple;

/**
 * Resolved the Selector resolution options to a single resolution
 * 
 * @author anjohnson
 * 
 */
public final class ResolutionResolver
{
	private static final Logger Log = LogManager.getLogger(ResolutionResolver.class);

	private ResolutionResolver()
	{
	}

	/**
	 * Resolve the resolution
	 * 
	 * @param job
	 * @return
	 */
	public static WidthHeightTuple resolveResolution(final EncodingJob job)
	{
		final Selector selector = job.getRequest().getSelector();
		final WidthHeightTuple oldResolution = tryGetResolution(job);
		WidthHeightTuple resolvedResolution = oldResolution;

		if (oldResolution == null)
		{
			Log.error("Could not get the original resolution of media file: " + job.getRequest().getSourceFile());
			return null;
		}

		if (selector.isForce169AspectRatio())
		{
			resolvedResolution = enforce169Resolution(oldResolution);
		}

		if (selector.isCapResolution())
		{
			final WidthHeightTuple maxResolution = new WidthHeightTuple(
					selector.getMaxHeight(),
					selector.getMaxWidth());
			resolvedResolution = enforceMaxResolution(
					oldResolution,
					maxResolution);
		}

		return resolvedResolution;
	}

	private static WidthHeightTuple tryGetResolution(final EncodingJob job)
	{
		final MediaInfoTrackSummary summary = MediaInfoTrackSummaryFactory.getSummary(job.getMediaInfo());
		final Collection<VideoTrack> videoTracks = summary.getVideoTracks();
		final VideoTrack chosenVideoTrack = getEarliestVideoTrack(videoTracks);

		return VideoTrackUtils.tryParsePixelAmount(chosenVideoTrack);
	}

	private static <T extends MediaTrack> T getEarliestVideoTrack(
			final Collection<T> tracks)
	{
		T lowestTrackId = null;
		for (final T t : tracks)
		{
			if (lowestTrackId == null)
			{
				lowestTrackId = t;
				continue;
			}

			if (t.getId() < lowestTrackId.getId())
			{
				lowestTrackId = t;
			}
		}

		return lowestTrackId;
	}

	private static WidthHeightTuple enforce169Resolution(
			final WidthHeightTuple oldResolution)
	{
		final int aspectCoefficient = IntegerUtils.gcd(
				oldResolution.getWidth(),
				oldResolution.getHeight());
		return new WidthHeightTuple(
				aspectCoefficient * 16,
				aspectCoefficient * 9);
	}

	private static WidthHeightTuple enforceMaxResolution(
			final WidthHeightTuple oldResolution,
			final WidthHeightTuple maxResolution)
	{
		if ((oldResolution.getWidth() <= maxResolution.getWidth()) && (oldResolution.getHeight() <= maxResolution.getHeight()))
		{
			return oldResolution;
		}

		final int aspectCoefficient = IntegerUtils.gcd(
				oldResolution.getWidth(),
				oldResolution.getHeight());
		final int widthCoPrime = oldResolution.getWidth() / aspectCoefficient;
		final int heightCoPrime = oldResolution.getHeight() / aspectCoefficient;

		final int closestCoefficient = maxResolution.getWidth() / heightCoPrime;

		return new WidthHeightTuple(
				closestCoefficient * widthCoPrime,
				closestCoefficient * heightCoPrime);

	}
}
