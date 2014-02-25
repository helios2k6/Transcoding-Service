package com.nlogneg.transcodingService.request;

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
	High444
}
