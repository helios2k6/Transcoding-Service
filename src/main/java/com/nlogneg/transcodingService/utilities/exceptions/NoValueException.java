package com.nlogneg.transcodingService.utilities.exceptions;

public class NoValueException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4740514533447519909L;

	/**
	 * 
	 */
	public NoValueException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public NoValueException(final String arg0, final Throwable arg1,
			final boolean arg2, final boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NoValueException(final String arg0, final Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public NoValueException(final String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public NoValueException(final Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
