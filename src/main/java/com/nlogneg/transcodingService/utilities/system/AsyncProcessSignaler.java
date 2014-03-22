package com.nlogneg.transcodingService.utilities.system;

import java.nio.channels.CompletionHandler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Represents an asynchronous external Process that will notify when the process
 * completes
 * 
 * @author anjohnson
 * 
 */
public final class AsyncProcessSignaler<T> implements Runnable
{
	private static final Logger Log = LogManager.getLogger(AsyncProcessSignaler.class);

	private final Process process;
	private final CompletionHandler<Void, T> completionHandler;
	private final T token;
	private volatile Thread currentThread;
	private volatile boolean isCancelled = false;
	private volatile boolean didFinishSuccessfully = false;

	public AsyncProcessSignaler(
			final Process process,
			final CompletionHandler<Void, T> completionHandler,
			final T token)
	{
		this.process = process;
		this.completionHandler = completionHandler;
		this.token = token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		this.currentThread = Thread.currentThread();
		while ((this.didFinishSuccessfully == false) && (this.isCancelled == false))
		{
			try
			{
				this.process.waitFor();
				this.didFinishSuccessfully = true;
			} catch (final InterruptedException e)
			{
				Log.error("Interrupted async process signaler.", e);
			}
		}

		if (this.didFinishSuccessfully)
		{
			this.completionHandler.completed(null, this.token);
		} else
		{
			this.completionHandler.failed(null, this.token);
		}
	}

	/**
	 * Cancels the current Async Process
	 */
	public void cancel()
	{
		this.isCancelled = true;
		this.currentThread.interrupt();
	}

	/**
	 * @return the isCancelled
	 */
	public boolean isCancelled()
	{
		return this.isCancelled;
	}

	/**
	 * @return Whether this async process finished successfully
	 */
	public boolean finishedSuccessfully()
	{
		return this.didFinishSuccessfully;
	}
}
