package com.nlogneg.serialization;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.requests.Request;
import com.nlogneg.transcodingService.serialization.SerializerFactory;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.thoughtworks.xstream.XStream;

@RunWith(JUnit4.class)
public class TestRequestDeserialization{

	@Test
	public void deserializeRequest() throws IOException{
		assertNotNull("Test file missing. Cannot perform test.", getClass().getResource("/video_request.xml"));

		InputStream resourceStream = getClass().getResourceAsStream("/video_request.xml");
		String resourceAsString = InputStreamUtilities.ReadInputStreamToEnd(resourceStream);
		resourceStream.close();
		
		XStream xstream = SerializerFactory.generateDefaultRequestSerializer();
		Request request = (Request)xstream.fromXML(resourceAsString);
		
		assertNotNull(request);
	}
}
