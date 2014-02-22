package com.nlogneg.transcodingService.media;


/**
 * A utility class for getting the file extensions for various media formats 
 * and codecs
 * @author Andrew
 *
 */
public final class MediaFileExtensions{
	
	/**
	 * Get the file extension for the audio format
	 * @param format The format
	 * @return The extension
	 */
	public static String getExtension(AudioCodecFormat format){
		switch(format) {
		case AAC:
			return ".aac";
		case DTS:
			return ".dts";
		case FLAC:
			return ".flac";
		case MP3:
			return ".mp3";
		case OGG:
			return ".ogg";
		case WAV:
			return ".wav";
		default:
			throw new RuntimeException("Invalid enum for audio codec format.");
		}
	}
	
	/**
	 * Get the file extension for the video format
	 * @param format The format
	 * @return The extension
	 */
	public static String getExtension(VideoCodecFormat format){
		switch(format){
		case H264:
			return ".264";
		case MPEG4_VISUAL:
			return ".xvid";
		default:
			throw new RuntimeException("Invalid enum for video codec format.");
		}
	}
	
	/**
	 * Get the file extension for the media container format
	 * @param format The format
	 * @return The extension
	 */
	public static String getExtension(MediaContainerFormat format){
		switch(format){
		case AVI:
			return ".avi";
		case MKV:
			return ".mkv";
		case MP4:
			return ".mp4";
		case OGM:
			return ".ogm";
		case WMV:
			return ".wmv";
		default:
			throw new RuntimeException("Invalid enum for media container format.");
		}
	}
}
