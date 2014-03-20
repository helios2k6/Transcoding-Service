package com.nlogneg.transcodingService.encoding.ffmpeg;

import java.util.LinkedList;
import java.util.List;

import com.nlogneg.transcodingService.encoding.DecoderArgumentBuilder;
import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.encoding.ResolutionResolver;
import com.nlogneg.transcodingService.encoding.SubtitleTrackOption;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.media.WidthHeightTuple;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Encapsulates the organization of ffmpeg decoding arguments
 * 
 * @author Andrew
 * 
 */
public final class FFMPEGVideoDecodingArgumentBuilder implements
		DecoderArgumentBuilder
{
	private static final String StandardOutArgument = "-";
	private static final String VideoCodecArgument = "-codec:v";
	private static final String MuteAudioArgument = "-an";
	private static final String InputFileArgument = "-i";
	private static final String PixelFormatArgument = "-pix_fmt";
	private static final String FormatArgument = "-f";

	private static final String RawVideoFormat = "rawvideo";
	private static final String Yuv4MpegPipeFormat = "yuv4mpegpipe";
	private static final String Yuv420pPixelFormat = "yuv420p";

	private List<String> calculateArgumentArray(final EncodingJob encodingJob)
	{
		final List<String> arguments = new LinkedList<String>();

		this.addFfmpegProcessName(arguments);

		this.muteAudio(arguments);
		this.addInputFile(arguments, encodingJob);

		this.addFilters(arguments, encodingJob);

		this.addVideoCodecFormat(arguments);
		this.addMediaContainerFormat(arguments);
		this.addPixelFormat(arguments);

		// Must be last
		this.addStandardOut(arguments);

		return arguments;
	}

	private void addFfmpegProcessName(final List<String> arguments)
	{
		arguments.add(0, SystemUtilities.getFFMPEGProcessName());
	}

	private void addFilters(final List<String> arguments, final EncodingJob job)
	{
		final WidthHeightTuple resolvedResolution = ResolutionResolver
				.resolveResolution(job);
		final SubtitleTrackOption option = job.getSubtitleTrackOption();
		final FFMPEGFilterArgumentBuilder builder = new FFMPEGFilterArgumentBuilder(
				Optional.make(resolvedResolution), option);

		builder.addVideoFilterArguments(arguments);
	}

	private void addInputFile(final List<String> arguments,
			final EncodingJob job)
	{
		arguments.add(InputFileArgument);
		arguments.add(job.getRequest().getSourceFile());
	}

	private void addStandardOut(final List<String> arguments)
	{
		arguments.add(StandardOutArgument);
	}

	private void addPixelFormat(final List<String> arguments)
	{
		// Pixel format
		arguments.add(PixelFormatArgument);
		arguments.add(Yuv420pPixelFormat);
	}

	private void addMediaContainerFormat(final List<String> arguments)
	{
		// Output format container
		arguments.add(FormatArgument);
		arguments.add(Yuv4MpegPipeFormat);
	}

	private void addVideoCodecFormat(final List<String> arguments)
	{
		// Output video codec selection
		arguments.add(VideoCodecArgument);
		arguments.add(RawVideoFormat);
	}

	private void muteAudio(final List<String> arguments)
	{
		// Mute audio
		arguments.add(MuteAudioArgument);
	}

	@Override
	public List<String> getDecoderArguments(final EncodingJob job)
	{
		return this.calculateArgumentArray(job);
	}
}
