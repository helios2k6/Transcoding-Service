package com.nlogneg.serialization;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.nlogneg.transcodingService.mediaInfo.MediaInfo;
import com.nlogneg.transcodingService.serialization.SerializerFactory;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.thoughtworks.xstream.XStream;

public class TestMediaInfoDeserialization{
	
	@Test
	public void deserializationOfVideoAudioTextTrack() throws IOException{
		assertNotNull("Test file missing. Cannot perform test.", getClass().getResource("/media_info_v_0_7_67.xml"));
		
		InputStream resourceStream = getClass().getResourceAsStream("/media_info_v_0_7_67.xml");
		String resourceAsString = InputStreamUtilities.ReadInputStreamToEnd(resourceStream);
		
		XStream deserializer = SerializerFactory.generateDefaultMediaInfoSerializer();
		MediaInfo info = (MediaInfo)deserializer.fromXML(resourceAsString);
		
		assertNotNull(info);
		assertNotNull(info.getFile());
	}
}
