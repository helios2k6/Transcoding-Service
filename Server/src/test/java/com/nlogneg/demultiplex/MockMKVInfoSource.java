package com.nlogneg.demultiplex;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import com.nlogneg.transcodingService.info.mkv.MKVInfoSource;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

public class MockMKVInfoSource implements MKVInfoSource{

	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.info.mkv.MKVInfoSource#tryGetMKVInfo(java.nio.file.Path)
	 */
	@Override
	public Optional<String> tryGetMKVInfo(Path mediaFilePath) {
		assertNotNull(
				"Test file missing. Cannot perform test.",
				this.getClass().getResource("/mkvinfo_output.txt"));
		
		final InputStream resourceStream = this.getClass().getResourceAsStream(
				"/mkvinfo_output.txt");
		
		String resourceAsString;
		try {
			resourceAsString = InputStreamUtilities.readInputStreamToEnd(resourceStream);
			resourceStream.close();
			return Optional.make(resourceAsString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.none();
		
	}

}
