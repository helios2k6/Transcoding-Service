package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the profile levels
 * @author anjohnson
 *
 */
public enum Profile implements Serializable{
	/**
	 * The main profile
	 */
	Main,
	/**
	 * The high profile
	 */
	High,
	/**
	 * The High 10-bit depth profile
	 */
	High10,
	/**
	 * The High 10-bit depth capable 4:2:2 color space profile
	 */
	High422,
	/**
	 * The High 10-bit depth capable 4:4:4 color space profile
	 */
	High444;
	
	/**
	 * Convert this Profile to x264 arguments
	 * @param profile
	 * @return
	 */
	public static List<String> convertToArguments(Profile profile){
		List<String> arguments = new ArrayList<String>();
		arguments.add("--profile");
		
		switch(profile){
		case High:
			arguments.add("high");
			break;
		case High10:
			arguments.add("high10");
			break;
		case High422:
			arguments.add("high422");
			break;
		case High444:
			arguments.add("high444");
			break;
		case Main:
			arguments.add("main");
			break;
		}
		
		return arguments;
	}
}
