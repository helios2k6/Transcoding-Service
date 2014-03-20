package com.nlogneg.transcodingService.utilities;

import fj.F;

/**
 * A collection of Projections
 * @author Andrew
 *
 */
public final class Projections{
	
	/**
	 * The standard identity projection
	 * @return
	 */
	public static <T> F<T, T> identity(){
		return new F<T, T>(){
			@Override
			public T f(T arg0) {
				return arg0;
			}
		};
	}
}
