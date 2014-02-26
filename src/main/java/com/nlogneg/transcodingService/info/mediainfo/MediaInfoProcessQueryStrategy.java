package com.nlogneg.transcodingService.info.mediainfo;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

public class MediaInfoProcessQueryStrategy implements MediaInfoQueryStrategy<Path, String>{
	private static final Logger Log = LogManager.getLogger(MediaInfoProcessQueryStrategy.class);

	private static final String OutputArgument = "--output=XML";

	@Override
	public Optional<String> queryMediaInfo(Path sourcePath) {
		ProcessBuilder builder = new ProcessBuilder(SystemUtilities.getMediaInfoProcessName(), OutputArgument, sourcePath.toAbsolutePath().toString());
		Log.info("Requesting media info about: " + sourcePath);
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
