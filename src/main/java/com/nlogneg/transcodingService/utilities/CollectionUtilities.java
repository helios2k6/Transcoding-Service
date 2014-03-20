package com.nlogneg.transcodingService.utilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fj.F;

/**
 * A collection of functions that act on collection objects
 * 
 * @author anjohnson
 * 
 */
public final class CollectionUtilities
{

	/**
	 * Returns the first element of the collection by using the iterator. Throws
	 * a runtime exception if the collection is empty
	 * 
	 * @param collection
	 *            The collection
	 * @return the first T
	 */
	public static <T> T first(final Collection<T> collection)
	{
		for (final T t : collection)
		{
			return t;
		}

		throw new RuntimeException("Collection was empty");
	}

	/**
	 * Takes a collection and projects it to a map
	 * 
	 * @param collection
	 * @return
	 */
	public static <T, K, V> Map<K, V> toMap(final Collection<T> collection,
			final F<T, K> keyProjection, final F<T, V> valueProjection)
	{

		final Map<K, V> result = new HashMap<>();

		for (final T t : collection)
		{
			final K key = keyProjection.f(t);
			final V value = valueProjection.f(t);
			result.put(key, value);
		}

		return result;
	}
}
