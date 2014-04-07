package com.nlogneg.transcodingService.demultiplex.fonts;

import java.nio.file.Path;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This installs fonts on Windows OS
 * 
 * @author anjohnson
 * 
 */
public final class WindowsFontInstaller implements FontInstaller
{
	private static final Logger Log = LogManager.getLogger(WindowsFontInstaller.class);

	@Override
	public boolean installFonts(
			final Collection<Path> fonts,
			final Path fontFolder)
	{
		Log.info("Installing Windows fonts");
		Log.warn("This does not install fonts on Windows.");

		return true;
	}
}
