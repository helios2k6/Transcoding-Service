package com.nlogneg.transcodingService.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Contains several system-dependent utilities 
 * @author anjohnson
 *
 */
public final class SystemUtilities{
	private static final Logger Log = LogManager.getLogger(SystemUtilities.class);
	
	private enum OperatingSystem{
		UnixLike,
		Windows,
	}
	
	private static final String mkvExtractCore = "mkvextract";
	private static final String mkvInfoCore = "mkvinfo";
	private static final String mediaInfoCore = "mediainfo";
	private static final String ffmpegCore = "ffmpeg";
	private static final String x264Core = "x264";
	
	private static final OperatingSystem OS = calculateOperatingSystem();

	private static OperatingSystem calculateOperatingSystem(){
		Log.info("Detecting operating system");
		
		String property = System.getProperty("os.name").toUpperCase();
		Log.info("os.name = " + property);
		
		if(property.contains("WINDOWS")){
			Log.info("Detected Windows");
			return OperatingSystem.Windows;
		}
		Log.info("Detected Unix-like OS");
		return OperatingSystem.UnixLike;
	}
	
	private static String calculateProcessName(String coreString){
		
		switch(OS){
		case Windows:
			return coreString + ".exe";
		case UnixLike:
			return coreString;
		default:
			throw new RuntimeException("Unable to determine operating system.");
		}
	}
	
	/**
	 * Get the mkvextract process name
	 * @return 
	 */
	public static String getMkvExtractProcessName(){
		return calculateProcessName(mkvExtractCore);
	}
	
	/**
	 * Get the mkvinfo process name
	 * @return
	 */
	public static String getMkvInfoProcessName(){
		return calculateProcessName(mkvInfoCore);
	}
	
	/**
	 * Get the mediainfo process name
	 * @return
	 */
	public static String getMediaInfoProcessName(){
		return calculateProcessName(mediaInfoCore);
	}
	
	/**
	 * Get the ffmpeg process name
	 * @return
	 */
	public static String getFFMPEGProcessName(){
		return calculateProcessName(ffmpegCore);
	}
	
	/**
	 * Get the x264 process name
	 * @return
	 */
	public static String getX264ProcessName(){
		return calculateProcessName(x264Core);
	}
}

