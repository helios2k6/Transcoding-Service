package com.nlogneg.transcodingService.utilities.threads;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public final class ProcessWriter implements Runnable
{
	private static final Logger Log = LogManager.getLogger(ProcessWriter.class);

	private final Process process;
	private final OutputStream stream;
	private final BlockingQueue<byte[]> queue;
	private final ExecutorService service;

	private volatile boolean isCancelled = false;
	private volatile boolean isFinished = false;

	/**
	 * @param process
	 * @param stream
	 * @param queue
	 * @param service
	 * @param isCancelled
	 */
	public ProcessWriter(final Process process, final OutputStream stream,
			final ExecutorService service, final BlockingQueue<byte[]> queue)
	{

		this.process = process;
		this.stream = stream;
		this.service = service;
		this.queue = queue;
	}

	@Override
	public void run()
	{
		final ProcessSignaler signaler = new ProcessSignaler(this.process);
		this.service.submit(signaler);

		while ((this.isCancelled == false) && (this.isFinished == false))
		{
			// Check to see if the external process is done
			if (signaler.isProcessFinished())
			{
				this.isFinished = true;
			} else
			{
				try
				{
					final byte[] chunk = this.queue.poll();
					if (chunk == null)
					{
						// Sleep for some time
						Thread.sleep(50);
					} else
					{
						this.stream.write(chunk);
					}
				} catch (final IOException e)
				{
					Log.error("Could not write to process stream.", e);
					this.isCancelled = true;
				} catch (final InterruptedException e)
				{
					Log.error("Process writer interrupted.", e);
				}
			}
		}

		this.checkCancelStatus(signaler);
	}

	/**
	 * Returns whether this Process Writer has been cancelled
	 * 
	 * @return the isCancelled
	 */
	public boolean isCancelled()
	{
		return this.isCancelled;
	}

	/**
	 * Returns whether or not this Process Reader finished reading from the
	 * process successfully. A cancelled Process Reader is not considered
	 * finished
	 * 
	 * @return
	 */
	public boolean isFinished()
	{
		return this.isFinished;
	}

	/**
	 * Cancels this Process Writer
	 */
	public void cancel()
	{
		this.isCancelled = true;
	}

	private void checkCancelStatus(final ProcessSignaler signaler)
	{
		if (this.isCancelled)
		{
			signaler.cancel();
		}
	}
}
