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
 * @author anjohnson
 *
 */
public final class UnixFontInstaller implements FontInstaller{
	private static final Logger Log = LogManager.getLogger(UnixFontInstaller.class);
	
	private static final String FontCacheProcessName = "fc-cache";
	private static final String ForceRefreshArgument = "-f";
	private static final String VerboseArgument = "-v";
	
	@Override
	public boolean installFonts(Collection<Path> fonts, Path fontFolder) {
		Log.info("Installing fonts on Linux");
		
		boolean moveResult = moveAllFonts(fonts, fontFolder);
		boolean refreshResult = refreshFontCache();
		
		Log.info("Finished installing fonts.");
		return moveResult && refreshResult;
	}

	private static boolean moveAllFonts(Collection<Path> fontFiles, Path fontFolder){
		boolean result = true;
		for(Path fontFile : fontFiles){
			Path destFontFile = fontFolder.resolve(fontFile.getFileName());
			Log.info("Installing font file: " + fontFile.toAbsolutePath().toString() + " to: " + destFontFile.toAbsolutePath().toString());
			try{
				Files.copy(fontFile, destFontFile);
			}catch (IOException e){
				Log.error("Could not move font.", e);
				result = false;
			}
		}
		
		return result;
	}
	
	private static boolean refreshFontCache(){
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
			
			return true;
		}catch (IOException e){
			Log.error("Could not refresh font cache.", e);
		}catch (InterruptedException e){
			Log.error("Thread interrupted on process.waitFor().", e);
		}
		
		return false;
	}
}
