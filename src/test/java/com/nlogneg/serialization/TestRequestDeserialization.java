package com.nlogneg.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.media.Compatibility;
import com.nlogneg.transcodingService.media.EncodingSettings;
import com.nlogneg.transcodingService.media.Estimation;
import com.nlogneg.transcodingService.media.Estimation.MotionEstimation;
import com.nlogneg.transcodingService.media.Level;
import com.nlogneg.transcodingService.media.Profile;
import com.nlogneg.transcodingService.media.PsychoVisualSettings;
import com.nlogneg.transcodingService.media.RateControl;
import com.nlogneg.transcodingService.media.RateControl.Type;
import com.nlogneg.transcodingService.media.SampleAspectRatio;
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
		
		assertNotNull(request);
		assertEquals("/test/file.mkv", request.getSourceFile());
		assertEquals("-", request.getDestinationFile());
		assertEquals(expectedEncodingSettings, request.getEncodingSettings());
	}
}
