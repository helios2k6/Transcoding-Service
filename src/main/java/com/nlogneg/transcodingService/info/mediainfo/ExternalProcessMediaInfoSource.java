package com.nlogneg.transcodingService.info.mediainfo;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * An external process that uses mediaInfo to query for media info
 * 
 * @author anjohnson
 * 
 */
public final class ExternalProcessMediaInfoSource implements MediaInfoSource
{
	private static final Logger Log = LogManager.getLogger(ExternalProcessMediaInfoSource.class);
	private static final String OutputArgument = "--output=XML";
	private static final ExternalProcessMediaInfoSource Instance = new ExternalProcessMediaInfoSource();

	/**
	 * Get the singleton instance of this class
	 * 
	 * @return
	 */
	public static ExternalProcessMediaInfoSource getInstance()
	{
		return Instance;
	}

	@Override
	public Optional<String> tryGetMediaInfo(final Path sourcePath)
	{
		final ProcessBuilder builder = new ProcessBuilder(
				SystemUtilities.getMediaInfoProcessName(),
				OutputArgument,
				sourcePath.toAbsolutePath().toString());
		Log.info("Requesting media info about: " + sourcePath);
		Process process;
		try
		{
			process = builder.start();

			final String output = InputStreamUtilities.readInputStreamToEnd(process.getInputStream());
			process.waitFor(); // Shouldn't take long.

			return Optional.make(output);
		} catch (final IOException e)
		{
			Log.error("An error occured while gathering media info.", e);
		} catch (final InterruptedException e)
		{
			Log.error(
					"This thread was interrupted while gathering media info.",
					e);
		}

		return Optional.none();
	}

}
