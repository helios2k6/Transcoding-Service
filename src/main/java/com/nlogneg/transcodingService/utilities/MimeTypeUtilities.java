package com.nlogneg.transcodingService.utilities;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParameterList;

/**
 * Static class of MIME Type utility functions
 * 
 * @author anjohnson
 * 
 */
public final class MimeTypeUtilities
{
	/**
	 * Determines if two MIME types are equal to one another
	 * 
	 * @param a
	 *            The first MIME type
	 * @param b
	 *            the second MIME type
	 * @return True if the two MIME types are equals. False otherwise
	 */
	public static final boolean areEqual(final MimeType a, final MimeType b)
	{
		if ((a == null) || (b == null))
		{
			throw new NullPointerException();
		}

		if (comparePrimaryType(a, b) == false)
		{
			return false;
		}

		if (compareSubType(a, b) == false)
		{
			return false;
		}

		if (compareParameterLists(a, b) == false)
		{
			return false;
		}

		return true;
	}

	private static boolean comparePrimaryType(final MimeType a, final MimeType b)
	{
		final String aPrimaryType = a.getPrimaryType();
		final String bPrimaryType = b.getPrimaryType();

		return aPrimaryType.equalsIgnoreCase(bPrimaryType);
	}

	private static boolean compareSubType(final MimeType a, final MimeType b)
	{
		final String aSubType = a.getSubType();
		final String bSubType = b.getSubType();

		return aSubType.equalsIgnoreCase(bSubType);
	}

	private static boolean compareParameterLists(final MimeType a,
			final MimeType b)
	{
		final MimeTypeParameterList aList = a.getParameters();
		final MimeTypeParameterList bList = b.getParameters();

		// Make set for A's parameters
		final Set<String> aNamesSet = new HashSet<String>();
		for (final Enumeration<String> aNames = aList.getNames(); aNames
				.hasMoreElements();)
		{
			aNamesSet.add(aNames.nextElement());
		}

		int bLength = 0;
		// Cycle through B's parameters and see if we can match everything up
		for (final Enumeration<String> bNames = bList.getNames(); bNames
				.hasMoreElements();)
		{
			final String nextBParameterName = bNames.nextElement();

			if (aNamesSet.contains(nextBParameterName) == false)
			{
				return false;
			}

			final String nextBParamValue = bList.get(nextBParameterName);

			final String correspondingAParamValue = aList
					.get(nextBParameterName);

			if (correspondingAParamValue == null)
			{
				return false;
			}

			if (nextBParamValue.equalsIgnoreCase(correspondingAParamValue) == false)
			{
				return false;
			}

			bLength++;
		}

		return aNamesSet.size() == bLength;
	}
}
