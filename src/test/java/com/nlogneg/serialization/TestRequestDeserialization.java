package com.nlogneg.serialization;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.request.incoming.Compatibility;
import com.nlogneg.transcodingService.request.incoming.EncodingSettings;
import com.nlogneg.transcodingService.request.incoming.Estimation;
import com.nlogneg.transcodingService.request.incoming.Level;
import com.nlogneg.transcodingService.request.incoming.Profile;
import com.nlogneg.transcodingService.request.incoming.PsychoVisualSettings;
import com.nlogneg.transcodingService.request.incoming.RateControl;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.request.incoming.SampleAspectRatio;
import com.nlogneg.transcodingService.request.incoming.Selector;
import com.nlogneg.transcodingService.request.incoming.Estimation.MotionEstimation;
import com.nlogneg.transcodingService.request.incoming.RateControl.Type;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;

@RunWith(JUnit4.class)
public class TestRequestDeserialization{

	@Test
	public void deserializeRequest() throws IOException{
		assertNotNull("Test file missing. Cannot perform test.", getClass().getResource("/video_request.xml"));

		InputStream resourceStream = getClass().getResourceAsStream("/video_request.xml");
		String resourceAsString = InputStreamUtilities.readInputStreamToEnd(resourceStream);
		resourceStream.close();
		
		XStream xstream = SerializerFactory.generateDefaultRequestSerializer();
		Request request = (Request)xstream.fromXML(resourceAsString);
		
		RateControl rateControl = new RateControl(Type.ConstantRateFactor, 32.0);
		Estimation estimation = new Estimation(MotionEstimation.UnevenMultiHexagon, 2, 10);
		Profile profile = Profile.High;
		Level level = new Level(4, 1);
		Compatibility compatibility = new Compatibility(new SampleAspectRatio(1, 1), true);
		PsychoVisualSettings psychoVisualSettings = new PsychoVisualSettings(0.3, 0.0);

		EncodingSettings expectedEncodingSettings = new EncodingSettings(rateControl, estimation, profile, level, compatibility, psychoVisualSettings);
		Selector expectedSelector = new Selector(false, false, false, -1, -1, -1);
		
		String expectedSourceFile = "/test/file.mkv";
		String expectedDestinationFile = "/STDOUT";
		
		assertNotNull(request);
		assertNotNull(request.getSourceFile());
		assertNotNull(request.getDestinationFile());
		
		assertTrue(expectedSourceFile.equals(request.getSourceFile()));
		assertTrue(expectedDestinationFile.equals(request.getDestinationFile()));
		
		assertEquals(expectedEncodingSettings, request.getEncodingSettings());
		assertEquals(expectedSelector, request.getSelector());
	}
}
