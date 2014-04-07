package com.nlogneg.transcodingService;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

/**
 * Controls relaying signals for all encoding jobs
 * 
 * @author anjohnson
 * 
 */
public class MasterJobController extends SimpleCommand
{

	/**
	 * Outgoing signal when a demultiplexing job fails
	 */
	public static final String DemultiplexJobFailure = "DemultiplexJobFailure";

	/**
	 * Outgoing signal when a demltiplexing job succeeds
	 */
	public static final String DemultiplexJobSuccess = "DemultiplexJobSuccess";

	/**
	 * Multiplex success signal
	 */
	public static final String MultiplexFileSuccess = "MultiplexFileSuccess";

	/**
	 * Multiplex failure signal
	 */
	public static final String MultiplexFileFailure = "MultiplexFileFailure";

	/**
	 * Encoding job success signal
	 */
	public static final String EncodingJobSuccess = "EncodingJobSuccess";

	/**
	 * Encoding job failure signal
	 */
	public static final String EncodingJobFailure = "EncodingJobFailure";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.puremvc.java.multicore.patterns.command.SimpleCommand#execute(org
	 * .puremvc.java.multicore.interfaces.INotification)
	 */
	@Override
	public void execute(final INotification notification)
	{

	}

}
