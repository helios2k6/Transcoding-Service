package com.nlogneg.transcodingService.utilities.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Monitors for the death of an external Process
 * 
 * @author anjohnson
 * 
 */
public final class ProcessSignaler implements Runnable
{
	private static final Logger Log = LogManager.getLogger(ProcessSignaler.class);

	private final Lock lock = new ReentrantLock();
	private volatile boolean isProcessFinished = false;
	private volatile boolean isCancelled = false;

	private final Process process;
	private Thread runningThread;

	public ProcessSignaler(final Process process)
	{
		this.process = process;
	}

	/**
	 * @return the isProcessFinished
	 */
	public boolean isProcessFinished()
	{
		return this.isProcessFinished;
	}

	/**
	 * Gets whether or not this process has been cancelled
	 * 
	 * @return
	 */
	public boolean isCancelled()
	{
		return this.isCancelled;
	}

	/**
	 * Cancels this Process Signaler
	 * 
	 * @throws InterruptedException
	 */
	public void cancel()
	{
		this.lock.lock();

		this.isCancelled = true;
		if (this.runningThread != null)
		{
			this.runningThread.interrupt();
		}

		this.lock.unlock();
	}

	@Override
	public void run()
	{
		this.setCurrentThread();

		while ((this.isProcessFinished == false) && (this.isCancelled == false))
		{
			try
			{
				this.process.waitFor();
				this.isProcessFinished = true;
			} catch (final InterruptedException e)
			{
				Log.error("Process Signaller interrupted", e);
			}
		}
	}

	private void setCurrentThread()
	{
		this.lock.lock();
		this.runningThread = Thread.currentThread();
		this.lock.unlock();
	}
}
