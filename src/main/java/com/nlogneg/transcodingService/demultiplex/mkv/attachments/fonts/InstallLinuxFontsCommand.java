package com.nlogneg.transcodingService.demultiplex.mkv.attachments.fonts;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.configuration.ServerConfigurationProxy;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;

/**
 * Install fonts on Linux
 * @author anjohnson
 *
 */
public final class InstallLinuxFontsCommand extends InstallFontsCommand{
	private static final Logger Log = LogManager.getLogger(InstallLinuxFontsCommand.class);
	
	private static final String FontCacheProcessName = "fc-cache";
	private static final String ForceRefreshArgument = "-f";
	private static final String VerboseArgument = "-v";
	
	@Override
	protected void installFonts(Collection<Path> fontFiles) {
		Log.info("Installing fonts on Linux");
		
		ServerConfigurationProxy proxy = (ServerConfigurationProxy)getFacade().retrieveProxy(ServerConfigurationProxy.PROXY_NAME);
		Path fontFolder = proxy.getConfigurationFile().getFontFolder();
		
		moveAllFonts(fontFiles, fontFolder);
		refreshFontCache();
	}

	private static void moveAllFonts(Collection<Path> fontFiles, Path fontFolder){
		for(Path fontFile : fontFiles){
			Path destFontFile = fontFolder.resolve(fontFile.getFileName());
			Log.info("Installing font file: " + fontFile.toAbsolutePath().toString() + " to: " + destFontFile.toAbsolutePath().toString());
			try{
				Files.copy(fontFile, destFontFile);
			}catch (IOException e){
				Log.error("Could not move font.", e);;
			}
		}
	}
	
	private static void refreshFontCache(){
		ProcessBuilder builder = new ProcessBuilder(FontCacheProcessName, ForceRefreshArgument, VerboseArgument);
		Log.info("Refreshing font cache");
		
		try {
			Process process = builder.start();
			
			InputStream standardOut = process.getInputStream();
			InputStream standardError = process.getErrorStream();
			
			String standardOutResult = InputStreamUtilities.readInputStreamToEnd(standardOut);
			String standardErrorResult = InputStreamUtilities.readInputStreamToEnd(standardError);
			
			process.waitFor();
			
			Log.debug("Font cache standard out result:\n" + standardOutResult);
			Log.debug("Font cache standard error result:\n" + standardErrorResult);
		}catch (IOException e){
			Log.error("Could not refresh font cache.", e);
		}catch (InterruptedException e){
			Log.error("Thread interrupted on process.waitFor().", e);
		}
	}
}
