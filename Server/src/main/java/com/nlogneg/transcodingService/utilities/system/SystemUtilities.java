package com.nlogneg.transcodingService.utilities.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains several system-dependent utilities
 * 
 * @author anjohnson
 * 
 */
public final class SystemUtilities
{
	private static final Logger Log = LogManager.getLogger(SystemUtilities.class);

	/**
	 * Denotes the Operating System
	 * 
	 * @author Andrew
	 * 
	 */
	public enum OperatingSystem
	{
		UnixLike, Windows,
	}

	private static final String MkvExtractCore = "mkvextract";
	private static final String MkvInfoCore = "mkvinfo";
	private static final String MediaInfoCore = "mediainfo";
	private static final String MP4BoxCore = "mp4box";
	private static final String FFMPEGCore = "ffmpeg";
	private static final String X264Core = "x264";
	private static final String NeroAacCore = "neroAacEnc";

	private static final OperatingSystem OS = calculateOperatingSystem();

	private static OperatingSystem calculateOperatingSystem()
	{
		Log.info("Detecting operating system");

		final String property = System.getProperty("os.name").toUpperCase();
		Log.info("os.name = " + property);

		if (property.contains("WINDOWS"))
		{
			Log.info("Detected Windows");
			return OperatingSystem.Windows;
		}
		Log.info("Detected Unix-like OS");
		return OperatingSystem.UnixLike;
	}

	private static String calculateProcessName(final String coreString)
	{

		switch (OS)
		{
		case Windows:
			return coreString + ".exe";
		case UnixLike:
			return coreString;
		default:
			throw new RuntimeException("Unable to determine operating system.");
		}
	}

	/**
	 * Gets the Operating System
	 * 
	 * @return
	 */
	public static OperatingSystem getOperatingSystem()
	{
		return OS;
	}

	/**
	 * Get the mkvextract process name
	 * 
	 * @return
	 */
	public static String getMkvExtractProcessName()
	{
		return calculateProcessName(MkvExtractCore);
	}

	/**
	 * Get the mkvinfo process name
	 * 
	 * @return
	 */
	public static String getMkvInfoProcessName()
	{
		return calculateProcessName(MkvInfoCore);
	}

	/**
	 * Get the mediainfo process name
	 * 
	 * @return
	 */
	public static String getMediaInfoProcessName()
	{
		return calculateProcessName(MediaInfoCore);
	}

	/**
	 * Get the ffmpeg process name
	 * 
	 * @return
	 */
	public static String getFFMPEGProcessName()
	{
		return calculateProcessName(FFMPEGCore);
	}

	/**
	 * Get the x264 process name
	 * 
	 * @return
	 */
	public static String getX264ProcessName()
	{
		return calculateProcessName(X264Core);
	}

	/**
	 * Get the neroAacEnc process name
	 * 
	 * @return
	 */
	public static String getNeroAacEncProcessName()
	{
		return calculateProcessName(NeroAacCore);
	}

	/**
	 * Get the mp4box process name
	 * 
	 * @return
	 */
	public static String getMP4BoxProcessName()
	{
		return calculateProcessName(MP4BoxCore);
	}
}
