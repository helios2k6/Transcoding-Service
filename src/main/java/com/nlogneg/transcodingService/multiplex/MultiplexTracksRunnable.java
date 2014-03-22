package com.nlogneg.transcodingService.multiplex;

import java.nio.channels.CompletionHandler;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;

public class MultiplexTracksRunnable implements Runnable
{
	private static final Logger Log = LogManager.getLogger(MultiplexTracksRunnable.class);
	private final MultiplexJob job;
	private final MultiplexArgumentBuilder builder;
	private final CompletionHandler<Void, MultiplexJob> callback;

	/**
	 * @param job
	 * @param builder
	 * @param callback
	 */
	public MultiplexTracksRunnable(
			final MultiplexJob job,
			final MultiplexArgumentBuilder builder,
			final CompletionHandler<Void, MultiplexJob> callback)
	{
		this.job = job;
		this.builder = builder;
		this.callback = callback;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		Log.info("Multiplexing files for job: " + this.job.getDestinationFile());

		final List<String> args = this.builder.getMultiplexingArguments(this.job);
		final ProcessBuilder builder = new ProcessBuilder(args);

		final Optional<Process> process = ProcessUtils.tryStartProcess(builder);
		if (process.isNone())
		{
			this.fail();
			return;
		}

		final boolean result = ProcessUtils.tryWaitForProcess(process.getValue());
		if (result)
		{
			Log.info("Successfully multiplexed job for: " + this.job.getDestinationFile());
			this.callback.completed(null, this.job);
		} else
		{
			this.fail();
		}
	}

	private void fail()
	{
		Log.error("Could not start multiplexing job for: " + this.job.getDestinationFile());
		this.callback.failed(null, this.job);
	}
}
