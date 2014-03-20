package com.nlogneg.transcodingService.utilities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * A collection of InputStream utility functions
 * @author anjohnson
 *
 */
public final class InputStreamUtilities{
	
	/**
	 * Read from an input stream until the end
	 * @param inputStream The input stream
	 * @return The string read from it
	 * @throws IOException  
	 */
	public static final String readInputStreamToEnd(InputStream inputStream) throws IOException{
		if(inputStream == null){
			throw new NullPointerException();
		}
		
		return IOUtils.toString(inputStream);
	}
	
	/**
	 * Read from an input stream until the end and return a byte array
	 * @param stream The stream
	 * @return A byte array
	 * @throws IOException
	 */
	public static final byte[] readRawInputStreamToEnd(InputStream stream) throws IOException{
		if(stream == null){
			throw new NullPointerException();
		}
		
		return IOUtils.toByteArray(stream);
	}
}
