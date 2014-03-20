package com.nlogneg.transcodingService.utilities.system;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A Process utility class that allows an interrelationship with the Optional
 * type
 * 
 * @author anjohnson
 * 
 */
public final class ProcessUtils
{
	private static final Logger Log = LogManager.getLogger(ProcessUtils.class);

	/**
	 * Attempts to start a process from a process builder
	 * 
	 * @param builder
	 *            The process builder
	 * @return An optional with the Started process
	 */
	public static Optional<Process> tryStartProcess(final ProcessBuilder builder)
	{
		Log.info("Attempting to start process.");
		try
		{
			final Process process = builder.start();
			Log.info("Process start successful.");
			return Optional.make(process);
		} catch (final IOException e)
		{
			Log.error("Could not start process.", e);
			;
		}

		return Optional.none();
	}

	/**
	 * Attempts to close the process
	 * 
	 * @param process
	 */
	public static void tryCloseProcess(final Optional<Process> process)
	{
		if (process.isSome())
		{
			process.getValue().destroy();
		}
	}

	/**
	 * Trys to wait for a process to end
	 * 
	 * @param process
	 * @return
	 */
	public static boolean tryWaitForProcess(final Process process)
	{
		try
		{
			process.waitFor();
		} catch (final InterruptedException e)
		{
			Log.error("Process waiting interrupted.", e);
			return false;
		}

		return true;
	}
}
