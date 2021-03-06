package com.nlogneg.transcodingService.utilities;

/**
 * Represents a regular tuple
 * 
 * @author anjohnson
 * 
 * @param <T>
 * @param <K>
 */
public class Tuple<T, K>
{
	private final T t;
	private final K k;

	/**
	 * @param t
	 * @param k
	 */
	public Tuple(final T t, final K k)
	{
		this.t = t;
		this.k = k;
	}

	public T item1()
	{
		return this.t;
	}

	public K item2()
	{
		return this.k;
	}
}
