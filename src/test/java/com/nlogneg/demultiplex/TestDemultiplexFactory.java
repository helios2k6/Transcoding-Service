package com.nlogneg.demultiplex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.demultiplex.DemultiplexJobFactory;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.nlogneg.transcodingService.utilities.Tuple;
import com.thoughtworks.xstream.XStream;

@RunWith(JUnit4.class)
public class TestDemultiplexFactory
{
	@Test
	public void testStandardDemultiplexJob() throws IOException
	{
		final Request request = this.createStandardRequest();
		final MediaInfo info = this.createStandardMediaInfo();
		
		final Optional<? extends DemultiplexJob> job = DemultiplexJobFactory.tryCreateDemultiplexJob(
				request,
				info,
				new MockMKVInfoSource());
		
		assertTrue(job.isSome());
		
		DemultiplexJob demultiplexJob = job.getValue();
		
		assertStandardAudioTrack(demultiplexJob, info);
	}

	private void assertStandardAudioTrack(DemultiplexJob job, MediaInfo info)
	{
		assertEquals(Paths.get("/test/file.mkv"), job.getMediaFile());
		assertEquals(info, job.getMediaInfo());
		
		//Assert the audio track
		Optional<Tuple<AudioTrack, Path>> audioTrackOptional = job.getAudioTrack();
		assertTrue(audioTrackOptional.isSome());
		Tuple<AudioTrack, Path> audioTrackTuple = audioTrackOptional.getValue();
		
		AudioTrack track = audioTrackTuple.item1();
		
		assertEquals("Vorbis", track.getFormat());
		assertEquals("Japanese", track.getLanguage());
	}
	
	private MediaInfo createStandardMediaInfo() throws IOException
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
	
	
	private Request createStandardRequest() throws IOException
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
	
	@Test
	public void testTwoAudioTracksNoForcedAudioTracks()
	{
		
	}
}
