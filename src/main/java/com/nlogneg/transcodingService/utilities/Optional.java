package com.nlogneg.transcodingService.utilities;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Represents an optional value
 * @author anjohnson
 *
 * @param <T> The enhanced type
 */
public abstract class Optional<T>{
	/**
	 * Gets whether this optional has a value
	 * @return Whether this is some value
	 */
	protected abstract boolean IsSome();
	
	/**
	 * Gets whether this optional has no value
	 * @return Whether this is has no value
	 */
	protected abstract boolean IsNone();
	
	/**
	 * Gets the value of this optional or throws a NoValueException if 
	 * this is a none optional type
	 * @return The value
	 * @throws NoValueException if there is no value
	 */
	protected abstract T getValue();
	
	/**
	 * Creates a optional type given a value
	 * @param t
	 * @return
	 */
	public static<T> Optional<T> Make(T t){
		if(t == null){
			
		}
		
		throw new NotImplementedException();
	}
	
	public String toString(){
		return "Optional value";
	}
}
