package com.nlogneg.transcodingService.transcoding.mkv;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Gathers raw MKVInfo from the external process "mkvinfo" which is a part of the
 * mkvtoolnix suite
 * @author anjohnson
 *
 */
public class MKVToolNixMKVInfoStrategy implements MKVInfoQueryStrategy<String, String>{

	private static final Logger Log = LogManager.getLogger(MKVToolNixMKVInfoStrategy.class);
	
	private static final String TrackArgument = "--track-info";
	
	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.transcoding.mkv.MKVInfoQueryStrategy#queryForMKVInfo(java.lang.String)
	 */
	@Override
	public Optional<String> queryForMKVInfo(String sourceFile){
		Log.info("Requesting MKV info about: " + sourceFile);
		
		ProcessBuilder builder = new ProcessBuilder(getProcessName(), TrackArgument, sourceFile);
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
	
	/**
	 * Gets the process name based on the operating sytem
	 * @return The expected mkvinfo process name
	 */
	private static String getProcessName(){
		String osProperty = System.getProperty("os.name");
		if(osProperty.contains("Windows")){
			return "mkvinfo.exe";
		}
		
		return "mkvinfo";
	}
}
