package com.nlogneg.transcodingService.utilities;

/**
 * Represents a None type
 * @author anjohnson
 *
 * @param <T>
 */
public final class None<T> extends Optional<T>{

	@Override
	public boolean isSome(){
		return false;
	}

	@Override
	public boolean isNone(){
		return true;
	}

	@Override
	public T getValue() {
		throw new NoValueException("Cannot get value for None type");
	}
	
	
	public String toString(){
		return "None value";
	}
}
