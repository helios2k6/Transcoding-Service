package com.nlogneg.transcodingService.info.mediainfo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.media.WidthHeightTuple;

/**
 * Utils for the VideoTrack class
 * @author anjohnson
 *
 */
public final class VideoTrackUtils {
	private static final Logger Log = LogManager.getLogger(VideoTrackUtils.class);
	/**
	 * Attempts to parse the pixel length given the video track
	 * @return
	 */
	public static WidthHeightTuple tryParsePixelAmount(VideoTrack track){
		int width = tryParsePixelAmount(track.getWidth());
		int height = tryParsePixelAmount(track.getHeight());
		
		if(width == -1 || height == -1){
			return null;
		}
		
		return new WidthHeightTuple(width, height);
	}

	private static int tryParsePixelAmount(String pixelAmount){
		String removedCommas = pixelAmount.replace(",", "");
		String[] split = removedCommas.split(" ");
		if(split.length > 1){
			try{
				return Integer.parseInt(split[0]);
			}catch(NumberFormatException e){
				Log.error("Could not parse pixels");
				return -1;
			}
		}

		return -1;
	}
}