package com.nlogneg.utilities;

import java.io.IOException;
import java.io.InputStream;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Testing utilities
 * @author anjohnson
 *
 */
public final class TestUtilities {
	
	/**
	 * Attempts to get a particular resource  
	 * @param path
	 * @return
	 */
	public static Optional<String> tryGetTestResource(String path)
	{
		try(final InputStream resourceStream = TestUtilities.class.getResourceAsStream(path);){
			return Optional.make(InputStreamUtilities.readInputStreamToEnd(resourceStream));
		} catch (IOException e){
			return Optional.none();
		}
	}
}
