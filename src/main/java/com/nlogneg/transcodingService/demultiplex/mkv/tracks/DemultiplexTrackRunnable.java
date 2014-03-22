package com.nlogneg.transcodingService.demultiplex.mkv.tracks;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.Tuple;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Demultiplexes a type of track
 * 
 * @author anjohnson
 * 
 */
public final class DemultiplexTrackRunnable implements Runnable
{
	private static final Logger Log = LogManager.getLogger(DemultiplexTrackRunnable.class);
	private static final String TracksArgument = "tracks";

	private final DemultiplexMKVJob job;
	private final CompletionHandler<Void, DemultiplexMKVJob> asyncCallback;

	/**
	 * @param job
	 * @param asyncCallback
	 */
	public DemultiplexTrackRunnable(
			final DemultiplexMKVJob job,
			final CompletionHandler<Void, DemultiplexMKVJob> asyncCallback)
	{
		this.job = job;
		this.asyncCallback = asyncCallback;
	}

	@Override
	public final void run()
	{
		final boolean audioTrackResult = this.extractAudioTrack();
		final boolean subtitleTrackResult = this.extractSubtitleTrack();
		final boolean finalResult = audioTrackResult && subtitleTrackResult;

		if (finalResult)
		{
			this.asyncCallback.completed(null, this.job);
		} else
		{
			this.asyncCallback.failed(null, this.job);
		}
	}

	private boolean extractAudioTrack()
	{
		Log.info("Extracting audio tracks");
		final Optional<Tuple<AudioTrack, Path>> audioTrackTupleOptional = this.job.getAudioTrack();

		if (audioTrackTupleOptional.isNone())
		{
			return true;
		}

		final Tuple<AudioTrack, Path> audioTrackTuple = audioTrackTupleOptional.getValue();
		return this.extractTrack(
				audioTrackTuple.item1(),
				audioTrackTuple.item2());
	}

	private boolean extractSubtitleTrack()
	{
		Log.info("Extracting subtitle tracks.");
		final Optional<Tuple<TextTrack, Path>> subtitleTrackTupleOptional = this.job.getSubtitleTrack();

		if (subtitleTrackTupleOptional.isNone())
		{
			return true;
		}

		final Tuple<TextTrack, Path> subtitleTrackTuple = subtitleTrackTupleOptional.getValue();
		return this.extractTrack(
				subtitleTrackTuple.item1(),
				subtitleTrackTuple.item2());
	}

	private boolean extractTrack(final MediaTrack track, final Path outputFile)
	{
		final String mediaFileName = this.job.getMediaFile().toAbsolutePath().toString();
		final StringBuilder argument = new StringBuilder();
		argument.append(track.getId()).append(":").append(
				outputFile.toAbsolutePath());

		final ProcessBuilder builder = new ProcessBuilder(
				SystemUtilities.getMkvExtractProcessName(),
				TracksArgument,
				mediaFileName,
				argument.toString());

		final Optional<Process> process = ProcessUtils.tryStartProcess(builder);

		if (process.isNone())
		{
			Log.error("Could not extract tracks for: " + this.job.getMediaFile());
			return false;
		}

		return ProcessUtils.tryWaitForProcess(process.getValue());
	}
}
