package com.nlogneg.transcodingService.utilities;

public class InvalidOperationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8806477723107914812L;

	/**
	 * 
	 */
	public InvalidOperationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public InvalidOperationException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InvalidOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public InvalidOperationException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public InvalidOperationException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	
}