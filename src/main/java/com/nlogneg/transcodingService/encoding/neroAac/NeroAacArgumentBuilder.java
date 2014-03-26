package com.nlogneg.transcodingService.encoding.neroAac;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import com.nlogneg.transcodingService.encoding.EncoderArgumentBuilder;
import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrackUtils;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Builds the arguments for the neroAacEnc program
 * 
 * @author anjohnson
 * 
 */
public final class NeroAacArgumentBuilder implements EncoderArgumentBuilder
{

	private static final String BitRateArgument = "-br";
	private static final String InputFileArgument = "-if";
	private static final String OutputFileArgument = "-of";
	private static final String LowComplexityArgument = "-lc";
	private static final String TwoPassArgument = "-2pass";

	@Override
	public List<String> getEncoderArguments(
			final EncodingJob job,
			final Path outputFile)
	{
		final List<String> arguments = new LinkedList<>();

		addProcessName(arguments);
		addInputFile(arguments, job);
		addBitRateArgument(arguments, job);
		addFlagArguments(arguments);
		addOutputArgument(arguments, outputFile);

		return arguments;
	}

	private static void addProcessName(final List<String> arguments)
	{
		arguments.add(0, SystemUtilities.getNeroAacEncProcessName());
	}

	private static void addInputFile(
			final List<String> arguments,
			final EncodingJob job)
	{
		arguments.add(InputFileArgument);
		arguments.add(job.getSourceFilePath().toAbsolutePath().toString());
	}

	private static void addBitRateArgument(
			final List<String> arguments,
			final EncodingJob job)
	{
		arguments.add(BitRateArgument);
		if (shouldUseHighBitRate(job))
		{
			arguments.add("256000");
		}
		else
		{
			arguments.add("96000");
		}
	}

	private static boolean shouldUseHighBitRate(final EncodingJob job)
	{
		final Optional<AudioTrack> audioTrack = job.getAudioTrackOption().getAudioTrack();
		if (audioTrack.isNone())
		{
			return false;
		}

		final AudioTrack track = audioTrack.getValue();
		if (AudioTrackUtils.getNumberOfChannels(track) > 2)
		{
			return true;
		}

		return false;
	}

	private static void addFlagArguments(final List<String> arguments)
	{
		arguments.add(LowComplexityArgument);
		arguments.add(TwoPassArgument);
	}

	private static void addOutputArgument(
			final List<String> arguments,
			final Path outputFile)
	{
		arguments.add(OutputFileArgument);
		arguments.add(outputFile.toAbsolutePath().toString());
	}
}
