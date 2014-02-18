package com.nlogneg.transcodingService.media;

import java.io.Serializable;

/**
 * Represents the H.264 level parameter
 * @author anjohnson
 *
 */
public final class Level implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3106477457601267041L;
	private final int major;
	private final int minor;
	
	/**
	 * Constructs a new level parameter
	 * @param major The major level
	 * @param minor The minor level
	 */
	public Level(int major, int minor){
		this.major = major;
		this.minor = minor;
	}

	/**
	 * Gets the major level
	 * @return the major
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * Gets the minor level
	 * @return the minor
	 */
	public int getMinor() {
		return minor;
	}
}
