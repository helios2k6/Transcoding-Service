package com.nlogneg.transcodingService.utilities;

/**
 * Represents a None type
 * @author anjohnson
 *
 * @param <T>
 */
public final class None<T> extends Optional<T>{

	@Override
	protected boolean IsSome(){
		return false;
	}

	@Override
	protected boolean IsNone(){
		return true;
	}

	@Override
	protected T getValue() {
		throw new NoValueException("Cannot get value for None type");
	}
	
	
	public String toString(){
		return "None value";
	}
}
