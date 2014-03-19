package com.nlogneg.transcodingService.utilities;

import com.nlogneg.transcodingService.utilities.exceptions.NoValueException;


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
	public abstract boolean isSome();
	
	/**
	 * Gets whether this optional has no value
	 * @return Whether this is has no value
	 */
	public abstract boolean isNone();
	
	/**
	 * Gets the value of this optional or throws a NoValueException if 
	 * this is a none optional type
	 * @return The value
	 * @throws NoValueException if there is no value
	 */
	public abstract T getValue();
	
	/**
	 * Creates a optional type given a value
	 * @param t The object to enhance
	 * @return a new optional 
	 */
	public static <T> Optional<T> make(T t){
		if(t == null){
			return new None<T>();
		}
		
		return new Some<T>(t);
	}
	
	/**
	 * Returns a new None optional type
	 * @return A new None optional type
	 */
	public static <T> Optional<T> none(){
		return new None<T>();
	}
	
	public String toString(){
		return "Optional value";
	}
}
