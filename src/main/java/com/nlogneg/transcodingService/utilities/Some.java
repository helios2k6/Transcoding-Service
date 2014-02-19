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
	protected boolean IsSome(){
		return true;
	}

	@Override
	protected boolean IsNone(){
		return false;
	}

	@Override
	protected T getValue(){
		return t;
	}
	
	public String toString(){
		return "Some value";
	}
}
