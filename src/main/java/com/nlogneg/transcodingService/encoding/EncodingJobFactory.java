package com.nlogneg.transcodingService.encoding;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A factory class for creating Encoding Jobs
 * 
 * @author anjohnson
 * 
 */
public final class EncodingJobFactory
{

	public static EncodingJob createEncodingJob(
			final Request request,
			final MediaInfo mediaInfo,
			final Optional<AudioTrack> demultiplexAudioTrack,
			final Optional<TextTrack> demultiplexSubtitleTrack,
			final Optional<Path> demultiplexedAudioTrackFile,
			final Optional<Path> demultiplexedSubtitlePath)
	{

		final AudioTrackOption audio = deduceAudioTrackOption(
				demultiplexAudioTrack,
				demultiplexedAudioTrackFile);
		final SubtitleTrackOption subs = deduceSubtitleTrackOption(
				demultiplexSubtitleTrack,
				demultiplexedSubtitlePath);

		final Path videoTrack = generateVideoFileName(request);
		Path audioTrack = null;

		if (audio.hasAudioTrackPath() && (audio.getEncodingAction() == EncodingAction.Encode))
		{
			audioTrack = generateAudioFileName(
					request,
					audio.getAudioTrack().getValue());
		}
		else if (demultiplexAudioTrack.isSome() && (audio.getEncodingAction() == EncodingAction.Multiplex))
		{
			audioTrack = demultiplexedAudioTrackFile.getValue();
		}

		return new EncodingJob(
				request,
				mediaInfo,
				audio,
				subs,
				videoTrack,
				audioTrack);
	}

	private static AudioTrackOption deduceAudioTrackOption(
			final Optional<AudioTrack> audioTrack,
			final Optional<Path> demultiplexAudioTrack)
	{

		if (audioTrack.isNone() && demultiplexAudioTrack.isNone())
		{
			return new AudioTrackOption(
					Optional.<Path> none(),
					EncodingAction.Ignore,
					Optional.<AudioTrack> none());
		}

		final AudioTrack track = audioTrack.getValue();
		EncodingAction action = EncodingAction.Encode;

		if (track.getFormat().equalsIgnoreCase("AAC"))
		{
			action = EncodingAction.Multiplex;
		}

		return new AudioTrackOption(demultiplexAudioTrack, action, audioTrack);
	}

	private static SubtitleTrackOption deduceSubtitleTrackOption(
			final Optional<TextTrack> demultiplexSubtitleTrack,
			final Optional<Path> demultiplexedSubtitlePath)
	{
		EncodingAction action = EncodingAction.Ignore;
		if (demultiplexSubtitleTrack.isSome())
		{
			action = EncodingAction.Encode;
		}

		return new SubtitleTrackOption(
				demultiplexedSubtitlePath,
				action,
				demultiplexSubtitleTrack);
	}

	private static Path generateVideoFileName(final Request request)
	{
		final String fileName = Paths.get(request.getSourceFile()).toAbsolutePath().toString();
		return Paths.get(fileName + "_temp_encoded.264");
	}

	private static Path generateAudioFileName(
			final Request request,
			final AudioTrack track)
	{
		final String fileName = Paths.get(request.getSourceFile()).toAbsolutePath().toString();
		return Paths.get(fileName + "_temp_encoded.m4a");
	}
}
