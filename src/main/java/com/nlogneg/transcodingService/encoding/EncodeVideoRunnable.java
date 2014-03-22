package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;
import com.nlogneg.transcodingService.utilities.threads.InterProcessPipe;

public final class EncodeVideoRunnable implements Runnable
{

	private static final Logger Log = LogManager.getLogger(EncodeVideoRunnable.class);

	private final EncodingJob job;
	private final DecoderArgumentBuilder decoderBuilder;
	private final EncoderArgumentBuilder encoderBuilder;
	private final CompletionHandler<Void, EncodingJob> callback;
	private final ExecutorService service;

	private volatile Thread runningThread;
	private volatile InterProcessPipe pipe;
	private volatile boolean isCancelled;

	/**
	 * @param job
	 * @param decoderBuilder
	 * @param encoderBuilder
	 * @param callback
	 * @param service
	 */
	public EncodeVideoRunnable(
			final EncodingJob job,
			final DecoderArgumentBuilder decoderBuilder,
			final EncoderArgumentBuilder encoderBuilder,
			final CompletionHandler<Void, EncodingJob> callback,
			final ExecutorService service)
	{
		this.job = job;
		this.decoderBuilder = decoderBuilder;
		this.encoderBuilder = encoderBuilder;
		this.callback = callback;
		this.service = service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		// Set current thread
		this.runningThread = Thread.currentThread();

		// Start decoder
		final Optional<Process> decoder = startDecoder(
				this.job,
				this.decoderBuilder);
		if (decoder.isNone())
		{
			Log.error("Could not begin ffmpeg decoder process for: " + this.job.getRequest().getSourceFile());
			return;
		}

		// Start encoder
		final Optional<Process> encoder = startEncoder(
				this.job,
				this.encoderBuilder);
		if (encoder.isNone())
		{
			Log.error("Could not begin x264 encoder process");
			ProcessUtils.tryCloseProcess(decoder);
			return;
		}

		// Get pipe
		final Optional<InterProcessPipe> pipeOptional = startPipe(
				decoder.getValue(),
				encoder.getValue(),
				this.service);
		if (pipeOptional.isNone())
		{
			Log.error("Could not create interprocess pipe");
			ProcessUtils.tryCloseProcess(decoder);
			ProcessUtils.tryCloseProcess(encoder);
			return;
		}

		this.pipe = pipeOptional.getValue();

		while (this.isCancelled == false)
		{
			this.pipe.pipe();
		}

		if (this.pipe.isFinished() && (this.isCancelled == false))
		{
			this.callback.completed(null, this.job);
		} else
		{
			this.callback.failed(null, this.job);
		}
	}

	/**
	 * Cancels the encoding runnable
	 */
	public void cancel()
	{
		// Set the cancelled flag
		this.isCancelled = true;

		// Cancel the pipe
		if (this.pipe != null)
		{
			this.pipe.cancel();
		}

		// Interrupt the thread
		if (this.runningThread != null)
		{
			this.runningThread.interrupt();
		}
	}

	private static Optional<InterProcessPipe> startPipe(
			final Process decoder,
			final Process encoder,
			final ExecutorService service)
	{
		return Optional.make(new InterProcessPipe(decoder, encoder, service));
	}

	private static Optional<Process> startEncoder(
			final EncodingJob job,
			final EncoderArgumentBuilder encoderBuilder)
	{
		final List<String> encodingOptions = encoderBuilder.getEncoderArguments(
				job,
				job.getDestinationFilePath());
		final ProcessBuilder processBuilder = new ProcessBuilder(
				encodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}

	private static Optional<Process> startDecoder(
			final EncodingJob job,
			final DecoderArgumentBuilder decoderBuilder)
	{
		final List<String> decodingOptions = decoderBuilder.getDecoderArguments(job);
		final ProcessBuilder processBuilder = new ProcessBuilder(
				decodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}
}
