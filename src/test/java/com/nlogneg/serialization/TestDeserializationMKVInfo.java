package com.nlogneg.serialization;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.transcoding.mkv.MKVInfo;
import com.nlogneg.transcodingService.transcoding.mkv.MKVToolNixRawMKVInfoDeserializerStrategy;
import com.nlogneg.transcodingService.transcoding.mkv.RawMKVInfoDeserializationStrategy;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

@RunWith(JUnit4.class)
public class TestDeserializationMKVInfo{

	@Test
	public void deserializeMkvInfoFlatFile() throws IOException{
		assertNotNull("Test file missing. Cannot perform test.", getClass().getResource("/mkvinfo_output.txt"));

		InputStream resourceStream = getClass().getResourceAsStream("/mkvinfo_output.txt");
		String resourceAsString = InputStreamUtilities.readInputStreamToEnd(resourceStream);
		resourceStream.close();
		
		RawMKVInfoDeserializationStrategy<String> strat = new MKVToolNixRawMKVInfoDeserializerStrategy();
		Optional<MKVInfo> info = strat.deserializeRawMKVInfo(resourceAsString);
		
		assertNotEquals(Optional.none(), info);
	}
}
