package com.nlogneg.transcodingService.transcoding.mkv;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SystemUtilities;

/**
 * Gathers raw MKVInfo from the external process "mkvinfo" which is a part of the
 * mkvtoolnix suite
 * @author anjohnson
 *
 */
public class MKVToolNixMKVInfoQueryStrategy implements MKVInfoQueryStrategy<String, String>{

	private static final Logger Log = LogManager.getLogger(MKVToolNixMKVInfoQueryStrategy.class);
	
	private static final String TrackArgument = "--track-info";
	
	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.transcoding.mkv.MKVInfoQueryStrategy#queryForMKVInfo(java.lang.String)
	 */
	@Override
	public Optional<String> queryForMKVInfo(String sourceFile){
		Log.info("Requesting MKV info about: " + sourceFile);
		
		ProcessBuilder builder = new ProcessBuilder(SystemUtilities.getMkvInfoProcessName(), TrackArgument, sourceFile);
		Process process;
		try {
			process = builder.start();
			
			String output = InputStreamUtilities.readInputStreamToEnd(process.getInputStream());
			
			process.waitFor();
			
			return Optional.make(output);
		} catch (IOException | InterruptedException e) {
			Log.error("An exception occured while getting the MKVInfo from an external process", e);
			return Optional.none();
		}
	}
}
