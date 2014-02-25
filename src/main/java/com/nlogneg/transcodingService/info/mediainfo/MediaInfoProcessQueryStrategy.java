package com.nlogneg.transcodingService.info.mediainfo;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SystemUtilities;

public class MediaInfoProcessQueryStrategy implements MediaInfoQueryStrategy<String, String>{
	private static final Logger Log = LogManager.getLogger(MediaInfoProcessQueryStrategy.class);

	private static final String OutputArgument = "--output=XML";

	@Override
	public Optional<String> queryMediaInfo(String sourceFile) {
		ProcessBuilder builder = new ProcessBuilder(SystemUtilities.getMediaInfoProcessName(), OutputArgument, sourceFile);
		Log.info("Requesting media info about: " + sourceFile);
		Process process;
		try {
			process = builder.start();
			
			String output = InputStreamUtilities.readInputStreamToEnd(process.getInputStream());
			process.waitFor(); //Shouldn't take long. 

			return Optional.make(output);
		} catch (IOException e) {
			Log.error("An error occured while gathering media info.", e);
		} catch (InterruptedException e) {
			Log.error("This thread was interrupted while gathering media info.", e);
		}
		
		return Optional.none();
	}

}
