package com.nlogneg.serialization;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MediaInfoDeserializationTests{
	
	@Test
	public void testDeserializationOfVideoAudioTextTrack(){
		assertNotNull("Test file missing. Cannot perform test.", getClass().getResource("/media_info_v_0_7_67.xml"));
	}
}
