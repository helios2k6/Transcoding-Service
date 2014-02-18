package com.nlogneg.transcodingService.media;

import java.io.Serializable;

/**
 * Represents the profile levels
 * @author anjohnson
 *
 */
public enum Profile implements Serializable{
	/**
	 * The main profile
	 */
	MainProfile,
	/**
	 * The high profile
	 */
	HighProfile,
	/**
	 * The High 10-bit depth profile
	 */
	High10Profile,
	/**
	 * The High 10-bit depth capable 4:2:2 color space profile
	 */
	High422Profile,
	/**
	 * The High 10-bit depth capable 4:4:4 color space profile
	 */
	High444Profile
}
