package com.nlogneg.transcodingService.demultiplex.mkv.attachments.fonts;

import java.nio.file.Path;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This installs fonts on Windows OS
 * @author anjohnson
 *
 */
public final class InstallWindowsFontsCommand extends InstallFontsCommand{
	private static final Logger Log = LogManager.getLogger(InstallWindowsFontsCommand.class);
	
	@Override
	protected void installFonts(Collection<Path> fontFiles){
		Log.info("Installing Windows fonts");
		Log.warn("This does not install fonts on Windows.");
	}
}
