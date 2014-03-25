package com.nlogneg.serialization;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.File;
import com.nlogneg.transcodingService.info.mediainfo.GeneralTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mediainfo.Track;
import com.nlogneg.transcodingService.info.mediainfo.TrackVisitor;
import com.nlogneg.transcodingService.info.mediainfo.VideoTrack;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.nlogneg.utilities.TestUtilities;
import com.thoughtworks.xstream.XStream;

@RunWith(JUnit4.class)
public class TestMediaInfoDeserialization
{

	private class TestVerificationTrackVisitor implements TrackVisitor
	{

		private void verifyBase(final Track track)
		{
			assertNotNull(track.getFormat());
		}

		private void verifyMediaBase(final MediaTrack track)
		{
			assertNotNull(track.getCodecID());
		}

		@Override
		public void visit(final Track track)
		{
			this.verifyBase(track);
		}

		@Override
		public void visit(final GeneralTrack track)
		{
			this.verifyBase(track);
			assertNotNull(track.getCompleteName());
		}

		@Override
		public void visit(final MediaTrack track)
		{
			this.verifyBase(track);
			this.verifyMediaBase(track);
		}

		@Override
		public void visit(final AudioTrack track)
		{
			this.verifyBase(track);
			this.verifyMediaBase(track);
			assertNotNull(track.getChannels());
			assertNotNull(track.getLanguage());
		}

		@Override
		public void visit(final TextTrack track)
		{
			this.verifyBase(track);
			this.verifyMediaBase(track);
			assertNotNull(track.getLanguage());
		}

		@Override
		public void visit(final VideoTrack track)
		{
			this.verifyBase(track);
			this.verifyMediaBase(track);
			assertNotNull(track.getDisplayAspectRatio());
			assertNotNull(track.getFrameRate());
			assertNotNull(track.getFrameRateMode());
			assertNotNull(track.getHeight());
			assertNotNull(track.getWidth());
		}
	}

	@Test
	public void deserializationOfVideoAudioTextTrack() throws IOException
	{
		Optional<String> mediaInfo = TestUtilities.tryGetTestResource("");
		
		assertTrue(mediaInfo.isSome());
		
		final XStream deserializer = SerializerFactory.generateDefaultMediaInfoSerializer();
		final MediaInfo info = (MediaInfo) deserializer.fromXML(mediaInfo.getValue());

		assertNotNull(info);

		final File file = info.getFile();
		assertNotNull(file);
		assertNotNull(file.getTracks());

		final TestVerificationTrackVisitor visitor = new TestVerificationTrackVisitor();
		for (final Track track : file.getTracks())
		{
			track.accept(visitor);
		}
	}
}
