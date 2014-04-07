package com.nlogneg.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.configuration.ConfigurationFile;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.nlogneg.utilities.TestUtilities;
import com.thoughtworks.xstream.XStream;

@RunWith(JUnit4.class)
public class TestDeserializationConfigFile
{

	@Test
	public void deserializeConfigFile() throws IOException
	{
		Optional<String> config = TestUtilities.tryGetTestResource("/deserializationTestResources/configuration_file.xml");
		
		assertTrue(config.isSome());
		
		final XStream deserializer = SerializerFactory.getConfigurationFileSerializer();
		final ConfigurationFile file = (ConfigurationFile) deserializer.fromXML(config.getValue());

		final Path expectedPath = Paths.get("C:\\Windows\\Fonts");
		assertEquals(expectedPath, file.getFontFolder());

		assertEquals(6787, file.getPortNumber());
	}
}
