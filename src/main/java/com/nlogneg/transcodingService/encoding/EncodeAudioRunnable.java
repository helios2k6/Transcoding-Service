package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;

/**
 * Encodes a file
 * 
 * @author Andrew
 * 
 */
public class EncodeAudioRunnable implements Runnable
{

	private static final Logger Log = LogManager
			.getLogger(EncodeAudioRunnable.class);

	private final EncodingJob job;
	private final CompletionHandler<Void, EncodingJob> callback;
	private final EncoderArgumentBuilder encoderArgumentBuilder;

	/**
	 * @param job
	 * @param callback
	 * @param encoderArgumentBuilder
	 */
	public EncodeAudioRunnable(
			final EncodingJob job,
			final CompletionHandler<Void, EncodingJob> callback,
			final EncoderArgumentBuilder encoderArgumentBuilder)
	{
		this.job = job;
		this.callback = callback;
		this.encoderArgumentBuilder = encoderArgumentBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		Log.info("Encoding audio for file: " + this.job.getSourceFilePath());

		final boolean result = this.encodeAudioTrack();
		if (result)
		{
			this.callback.completed(null, this.job);
		} else
		{
			this.callback.failed(null, this.job);
		}
	}

	private boolean encodeAudioTrack()
	{
		final Optional<Path> audioTrackPathOptional = this.job
				.getAudioTrackOption().getAudioTrackFilePath();

		if (audioTrackPathOptional.isNone())
		{
			Log.error("Could not encode audio track. No audio track path specified.");
			return false;
		}

		final List<String> arguments = this.encoderArgumentBuilder
				.getEncoderArguments(this.job, this.job.getOutputAudioFile());
		final Optional<Process> process = ProcessUtils
				.tryStartProcess(new ProcessBuilder(arguments));

		if (process.isNone())
		{
			Log.error("Could not start encoder process");
			return false;
		}

		return ProcessUtils.tryWaitForProcess(process.getValue());
	}
}
