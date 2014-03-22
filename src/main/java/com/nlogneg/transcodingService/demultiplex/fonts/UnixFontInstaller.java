package com.nlogneg.transcodingService.demultiplex.fonts;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;

/**
 * Install fonts on Linux
 * 
 * @author anjohnson
 * 
 */
public final class UnixFontInstaller implements FontInstaller
{
	private static final Logger Log = LogManager.getLogger(UnixFontInstaller.class);

	private static final String FontCacheProcessName = "fc-cache";
	private static final String ForceRefreshArgument = "-f";
	private static final String VerboseArgument = "-v";

	@Override
	public boolean installFonts(
			final Collection<Path> fonts,
			final Path fontFolder)
	{
		Log.info("Installing fonts on Linux");

		final boolean moveResult = moveAllFonts(fonts, fontFolder);
		final boolean refreshResult = refreshFontCache();

		Log.info("Finished installing fonts.");
		return moveResult && refreshResult;
	}

	private static boolean moveAllFonts(
			final Collection<Path> fontFiles,
			final Path fontFolder)
	{
		boolean result = true;
		for (final Path fontFile : fontFiles)
		{
			final Path destFontFile = fontFolder.resolve(fontFile.getFileName());
			Log.info("Installing font file: " + fontFile.toAbsolutePath().toString() + " to: " + destFontFile.toAbsolutePath().toString());
			try
			{
				Files.copy(fontFile, destFontFile);
			} catch (final IOException e)
			{
				Log.error("Could not move font.", e);
				result = false;
			}
		}

		return result;
	}

	private static boolean refreshFontCache()
	{
		final ProcessBuilder builder = new ProcessBuilder(
				FontCacheProcessName,
				ForceRefreshArgument,
				VerboseArgument);
		Log.info("Refreshing font cache");

		try
		{
			final Process process = builder.start();

			final InputStream standardOut = process.getInputStream();
			final InputStream standardError = process.getErrorStream();

			final String standardOutResult = InputStreamUtilities.readInputStreamToEnd(standardOut);
			final String standardErrorResult = InputStreamUtilities.readInputStreamToEnd(standardError);

			process.waitFor();

			Log.debug("Font cache standard out result:\n" + standardOutResult);
			Log.debug("Font cache standard error result:\n" + standardErrorResult);

			return true;
		} catch (final IOException e)
		{
			Log.error("Could not refresh font cache.", e);
		} catch (final InterruptedException e)
		{
			Log.error("Thread interrupted on process.waitFor().", e);
		}

		return false;
	}
}
