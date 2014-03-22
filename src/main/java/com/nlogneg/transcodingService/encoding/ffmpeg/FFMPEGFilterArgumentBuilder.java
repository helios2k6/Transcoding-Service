package com.nlogneg.transcodingService.encoding.ffmpeg;

import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.encoding.EncodingAction;
import com.nlogneg.transcodingService.encoding.SubtitleTrackOption;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.media.WidthHeightTuple;

/**
 * Represents an argument builder that corresponds to the FFMPEG filter system
 * 
 * @author anjohnson
 * 
 */
public final class FFMPEGFilterArgumentBuilder
{
	private static final Logger Log = LogManager.getLogger(FFMPEGFilterArgumentBuilder.class);

	private final Optional<WidthHeightTuple> resizeResolution;
	private final SubtitleTrackOption subtitleOverlay;

	/**
	 * @param resizeResolution
	 * @param subtitleOverlay
	 */
	public FFMPEGFilterArgumentBuilder(
			final Optional<WidthHeightTuple> resizeResolution,
			final SubtitleTrackOption subtitleOverlay)
	{
		this.resizeResolution = resizeResolution;
		this.subtitleOverlay = subtitleOverlay;
	}

	public void addVideoFilterArguments(final List<String> arguments)
	{
		if ((this.shouldResize() == false) && (this.subtitleOverlay.getEncodingActions() != EncodingAction.Encode))
		{
			return;
		}

		arguments.add("-vf");

		final StringBuilder finalArgumentBuilder = new StringBuilder();

		final String subtitleArgumentPart = this.getSubtitleOverlayArgument();
		if (subtitleArgumentPart != null)
		{
			finalArgumentBuilder.append(subtitleArgumentPart);
		}

		final String resizeArgument = this.getResizeArgument();
		if (resizeArgument != null)
		{
			finalArgumentBuilder.append(",").append(resizeArgument);
		}

		arguments.add(finalArgumentBuilder.toString());
	}

	private boolean shouldResize()
	{
		return this.resizeResolution.isSome();
	}

	private String getSubtitleOverlayArgument()
	{
		if (this.subtitleOverlay.getEncodingActions() == EncodingAction.Encode)
		{
			final Optional<Path> extractedOptionalSubtitleFile = this.subtitleOverlay.getTextTrackFilePath();
			if (extractedOptionalSubtitleFile.isSome())
			{
				final Path extractedSubtitleFile = extractedOptionalSubtitleFile.getValue();

				final StringBuilder argBuilder = new StringBuilder();
				argBuilder.append("\"").append("ass=").append(
						extractedSubtitleFile.toAbsolutePath().toString()).append(
						"\"");

				return argBuilder.toString();
			} else
			{
				Log.error("Subtitle track option was ENCODE but no extracted subtitle track could be found");
			}
		}

		return null;
	}

	private String getResizeArgument()
	{
		if (this.resizeResolution.isSome())
		{
			final StringBuilder argumentBuilder = new StringBuilder();
			argumentBuilder.append("scale=").append(
					this.resizeResolution.getValue().getWidth());
			argumentBuilder.append(":").append(
					this.resizeResolution.getValue().getHeight()).append(";");
			return argumentBuilder.toString();
		}

		return null;
	}
}
