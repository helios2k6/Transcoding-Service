package com.nlogneg.transcodingService.utilities;

import java.util.List;

/**
 * List utilities
 * @author anjohnson
 *
 */
public final class ListUtilities{
	
	/**
	 * Flatten a list of Strings
	 * @param strings The strings
	 * @return A flattened string
	 */
	public static String flatten(List<String> strings){
		StringBuilder builder = new StringBuilder();
		
		for(String line : strings){
			builder.append(line);
		}
		
		return builder.toString();
	}
}
