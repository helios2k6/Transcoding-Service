package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Level other = (Level) obj;
		if (major != other.major) {
			return false;
		}
		if (minor != other.minor) {
			return false;
		}
		return true;
	}
	
	/**
	 * Converts the Level to arguments for x264
	 * @param level
	 * @return
	 */
	public static List<String> convertToArguments(Level level){
		StringBuilder builder = new StringBuilder();
		builder.append(level.getMajor()).append(".").append(level.getMinor());
		
		List<String> arguments = new ArrayList<String>();
		
		arguments.add("--level");
		arguments.add(builder.toString());
		
		return arguments;
	}
}
