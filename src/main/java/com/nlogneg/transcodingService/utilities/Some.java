package com.nlogneg.transcodingService.utilities;


/**
 * Represents Some value
 * @author anjohnson
 *
 * @param <T> The type that has been enhanced
 */
public class Some<T> extends Optional<T>{
	
	private final T t;
	
	public Some(T t){
		this.t = t;
	}
	
	@Override
	public boolean isSome(){
		return true;
	}

	@Override
	public boolean isNone(){
		return false;
	}

	@Override
	public T getValue(){
		return t;
	}
	
	public String toString(){
		return "Some value";
	}
}
