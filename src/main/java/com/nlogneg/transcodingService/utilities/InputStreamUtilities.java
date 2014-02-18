package com.nlogneg.transcodingService.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	public static final String ReadInputStreamToEnd(InputStream inputStream) throws IOException{
		if(inputStream == null) throw new NullPointerException();
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));){
			String currentLine = reader.readLine();
			StringBuffer buffer = new StringBuffer();
			
			while(currentLine != null){
				buffer.append(currentLine);
				currentLine = reader.readLine();
			}
			
			return buffer.toString();
		}
	}
}
