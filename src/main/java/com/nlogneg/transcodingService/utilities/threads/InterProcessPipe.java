package com.nlogneg.transcodingService.utilities.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Represents a pipe between two processes. This object must be run in a
 * separate thread so that it can promptly feed data between the source and sink
 * processes.
 * 
 * @author Andrew
 * 
 */
public final class InterProcessPipe
{
	private static final Logger Log = LogManager
			.getLogger(InterProcessPipe.class);

	private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
	private final ProcessReader reader;
	private final ProcessWriter writer;
	private final ExecutorService service;

	private volatile boolean isCancelled = false;
	private volatile boolean isFinished = false;

	/**
	 * Constructs an InterProcessPipe line
	 * 
	 * @param source
	 *            The source process
	 * @param sink
	 *            The sink process
	 * @param service
	 *            The executor service
	 * 
	 */
	public InterProcessPipe(final Process source, final Process sink,
			final ExecutorService service)
	{
		this.reader = new ProcessReader(source, source.getInputStream(),
				service, this.queue);
		this.writer = new ProcessWriter(sink, sink.getOutputStream(), service,
				this.queue);
		this.service = service;
	}

	/**
	 * Pipes data between two processes
	 */
	public void pipe()
	{
		Log.info("Processing inter-process communication");

		this.service.submit(this.reader);
		this.service.submit(this.writer);

		while ((this.isCancelled == false) && (this.isFinished == false))
		{
			final boolean readerFinished = this.reader.isFinished();
			final boolean writerFinished = this.writer.isFinished();

			if (readerFinished && writerFinished)
			{
				this.isFinished = true;
			}

			try
			{
				Thread.sleep(150);
			} catch (final InterruptedException e)
			{
				Log.error("Interprocess Pipe interrupted.", e);
			}
		}

		if (this.isCancelled)
		{
			this.reader.cancel();
			this.writer.cancel();
		}
	}

	/**
	 * Gets whether this pipe is cancelled
	 * 
	 * @return
	 */
	public boolean isCancelled()
	{
		return this.isCancelled;
	}

	/**
	 * Cancels this pipe
	 */
	public void cancel()
	{
		this.isCancelled = true;
	}

	/**
	 * Gets whether this pipe finished
	 * 
	 * @return
	 */
	public boolean isFinished()
	{
		return this.isFinished;
	}
}
