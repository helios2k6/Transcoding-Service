package com.nlogneg.transcodingService.utilities;

import java.util.Collection;

/**
 * A collection of functions that act on collection objects
 * @author anjohnson
 *
 */
public final class CollectionUtilities{
	
	/**
	 * Returns the first element of the collection by using the iterator. 
	 * Throws a runtime exception if the collection is empty
	 * @param collection The collection
	 * @return the first T
	 */
	public static <T> T first(Collection<T> collection){
		for(T t : collection){
			return t;
		}
		
		throw new RuntimeException("Collection was empty");
	}
}
