package com.nlogneg.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.configuration.ConfigurationFile;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;

@RunWith(JUnit4.class)
public class TestDeserializationConfigFile {
	
	@Test
	public void deserializeConfigFile() throws IOException{
		assertNotNull("Test file missing. Cannot perform test.", getClass().getResource("/configuration_file.xml"));
		
		InputStream resourceStream = getClass().getResourceAsStream("/configuration_file.xml");
		String resourceAsString = InputStreamUtilities.readInputStreamToEnd(resourceStream);
		resourceStream.close();
		
		XStream deserializer = SerializerFactory.getConfigurationFileSerializer();
		ConfigurationFile file = (ConfigurationFile)deserializer.fromXML(resourceAsString);
		
		Path expectedPath = Paths.get("C:\\Windows\\Fonts");
		assertEquals(expectedPath, file.getFontFolder());
		
		assertEquals(6787, file.getPortNumber());
	}
}
