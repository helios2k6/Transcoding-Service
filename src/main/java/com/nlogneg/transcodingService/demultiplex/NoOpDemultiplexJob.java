package com.nlogneg.transcodingService.demultiplex;


/**
 * Represents a demultiplex job that does nothing. Specifically used for files
 * that are passed in that aren't media files
 * @author anjohnson
 *
 */
public final class NoOpDemultiplexJob extends DemultiplexJob{

	public NoOpDemultiplexJob(){
		super(null, null, null, null);
	}
	
}
