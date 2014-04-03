package com.nlogneg.transcodingService.utilities;

import java.util.List;

/**
 * List utilities
 * 
 * @author anjohnson
 * 
 */
public final class ListUtilities
{

	/**
	 * Flatten a list of Strings
	 * 
	 * @param strings
	 *            The strings
	 * @return A flattened string
	 */
	public static String flatten(final List<String> strings)
	{
		final StringBuilder builder = new StringBuilder();

		for (final String line : strings)
		{
			builder.append(line).append("\n");
		}

		return builder.toString();
	}
}
