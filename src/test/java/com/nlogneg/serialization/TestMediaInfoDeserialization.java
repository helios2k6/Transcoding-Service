package com.nlogneg.serialization;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

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
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;

@RunWith(JUnit4.class)
public class TestMediaInfoDeserialization{
	
	private class TestVerificationTrackVisitor implements TrackVisitor{

		private void verifyBase(Track track){
			assertNotNull(track.getFormat());
		}
		
		private void verifyMediaBase(MediaTrack track){
			assertNotNull(track.getCodecID());
		}
		
		@Override
		public void visit(Track track){
			verifyBase(track);
		}

		@Override
		public void visit(GeneralTrack track){
			verifyBase(track);
			assertNotNull(track.getCompleteName());
		}

		@Override
		public void visit(MediaTrack track){
			verifyBase(track);
			verifyMediaBase(track);
		}

		@Override
		public void visit(AudioTrack track){
			verifyBase(track);
			verifyMediaBase(track);
			assertNotNull(track.getChannels());
			assertNotNull(track.getLanguage());
		}

		@Override
		public void visit(TextTrack track){
			verifyBase(track);
			verifyMediaBase(track);
			assertNotNull(track.getLanguage());
		}

		@Override
		public void visit(VideoTrack track){
			verifyBase(track);
			verifyMediaBase(track);
			assertNotNull(track.getDisplayAspectRatio());
			assertNotNull(track.getFrameRate());
			assertNotNull(track.getFrameRateMode());
			assertNotNull(track.getHeight());
			assertNotNull(track.getWidth());
		}
	}
	
	@Test
	public void deserializationOfVideoAudioTextTrack() throws IOException{
		assertNotNull("Test file missing. Cannot perform test.", getClass().getResource("/media_info_v_0_7_67.xml"));
		
		InputStream resourceStream = getClass().getResourceAsStream("/media_info_v_0_7_67.xml");
		String resourceAsString = InputStreamUtilities.readInputStreamToEnd(resourceStream);
		resourceStream.close();
		
		XStream deserializer = SerializerFactory.generateDefaultMediaInfoSerializer();
		MediaInfo info = (MediaInfo)deserializer.fromXML(resourceAsString);
		
		assertNotNull(info);
		
		File file = info.getFile();
		assertNotNull(file);
		assertNotNull(file.getTracks());
		
		TestVerificationTrackVisitor visitor = new TestVerificationTrackVisitor();
		for(Track track : file.getTracks()){
			track.accept(visitor);
		}
	}
}
