package com.nlogneg.transcodingService.utilities.system;

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
	
	private static final String MkvExtractCore = "mkvextract";
	private static final String MkvInfoCore = "mkvinfo";
	private static final String MediaInfoCore = "mediainfo";
	private static final String FFMPEGCore = "ffmpeg";
	private static final String X264Core = "x264";
	private static final String NeroAacCore = "neroAacEnc";
	
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
		return calculateProcessName(MkvExtractCore);
	}
	
	/**
	 * Get the mkvinfo process name
	 * @return
	 */
	public static String getMkvInfoProcessName(){
		return calculateProcessName(MkvInfoCore);
	}
	
	/**
	 * Get the mediainfo process name
	 * @return
	 */
	public static String getMediaInfoProcessName(){
		return calculateProcessName(MediaInfoCore);
	}
	
	/**
	 * Get the ffmpeg process name
	 * @return
	 */
	public static String getFFMPEGProcessName(){
		return calculateProcessName(FFMPEGCore);
	}
	
	/**
	 * Get the x264 process name
	 * @return
	 */
	public static String getX264ProcessName(){
		return calculateProcessName(X264Core);
	}
	
	/**
	 * Get the neroAacEnc process name
	 * @return
	 */
	public static String getNeroAacEncProcessName(){
		return calculateProcessName(NeroAacCore);
	}
}

