package com.nlogneg.demultiplex;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.demultiplex.DemultiplexJobFactory;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;

@RunWith(JUnit4.class)
public class TestDemultiplexFactory
{

	@Test
	public void createDemultiplexJob() throws IOException
	{
		final Request request = this.createRequest();
		final MediaInfo info = this.createMediaInfo();
		
		final Optional<? extends DemultiplexJob> job = DemultiplexJobFactory.tryCreateDemultiplexJob(
				request,
				info);
		
		assertTrue(job.isSome());
	}

	private MediaInfo createMediaInfo() throws IOException
	{
		assertNotNull(
				"Test file missing. Cannot perform test.",
				this.getClass().getResource("/media_info_v_0_7_67.xml"));

		final InputStream resourceStream = this.getClass().getResourceAsStream(
				"/media_info_v_0_7_67.xml");
		final String resourceAsString = InputStreamUtilities.readInputStreamToEnd(resourceStream);
		resourceStream.close();

		final XStream deserializer = SerializerFactory.generateDefaultMediaInfoSerializer();
		final MediaInfo info = (MediaInfo) deserializer.fromXML(resourceAsString);
		assertNotNull(info);
		return info;
	}
	
	
	private Request createRequest() throws IOException
	{
		assertNotNull(
				"Test file missing. Cannot perform test.",
				this.getClass().getResource("/video_request.xml"));

		final InputStream resourceStream = this.getClass().getResourceAsStream(
				"/video_request.xml");
		final String resourceAsString = InputStreamUtilities.readInputStreamToEnd(resourceStream);
		resourceStream.close();

		final XStream xstream = SerializerFactory.generateDefaultRequestSerializer();
		final Request request = (Request) xstream.fromXML(resourceAsString);
		assertNotNull(request);
		return request;
	}
}
