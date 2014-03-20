package com.nlogneg.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.info.mkv.MKVInfo;
import com.nlogneg.transcodingService.info.mkv.MKVInfoDeserializer;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

@RunWith(JUnit4.class)
public class TestDeserializationMKVInfo{

	@Test
	public void deserializeMkvInfoFlatFile() throws IOException, MimeTypeParseException{
		assertNotNull("Test file missing. Cannot perform test.", getClass().getResource("/mkvinfo_output.txt"));
		
		Map<Integer, Attachment> expectedAttachmentMap = new HashMap<Integer, Attachment>();
		MimeType fontMime = new MimeType("application/x-truetype-font");

		//Initialize expected map
		{
			expectedAttachmentMap.put(Integer.valueOf(1), new Attachment(1, "HUM521B.TTF", fontMime, 3373695145L));
			expectedAttachmentMap.put(Integer.valueOf(2), new Attachment(2, "Hum521Bd.ttf", fontMime, 384468372L));
			expectedAttachmentMap.put(Integer.valueOf(3), new Attachment(3, "HUM521BI.TTF", fontMime, 2065768369L));
			expectedAttachmentMap.put(Integer.valueOf(4), new Attachment(4, "HUM521I.TTF", fontMime, 1998928003L));
			expectedAttachmentMap.put(Integer.valueOf(5), new Attachment(5, "Hum521It.ttf", fontMime, 655547026L));
			expectedAttachmentMap.put(Integer.valueOf(6), new Attachment(6, "HUM521N.TTF", fontMime, 2586110364L));
			expectedAttachmentMap.put(Integer.valueOf(7), new Attachment(7, "Hum521Rm.ttf", fontMime, 463637135L));
			expectedAttachmentMap.put(Integer.valueOf(8), new Attachment(8, "TektonPro-Bold.otf", fontMime, 2772423610L));
			expectedAttachmentMap.put(Integer.valueOf(9), new Attachment(9, "TektonPro-BoldCond.otf", fontMime, 2506718374L));
			expectedAttachmentMap.put(Integer.valueOf(10), new Attachment(10, "TektonPro-BoldExt.otf", fontMime, 3107833225L));
			expectedAttachmentMap.put(Integer.valueOf(11), new Attachment(11, "TektonPro-BoldObl.otf", fontMime, 2121344399L));
		}
		
		InputStream resourceStream = getClass().getResourceAsStream("/mkvinfo_output.txt");
		String resourceAsString = InputStreamUtilities.readInputStreamToEnd(resourceStream);
		resourceStream.close();

		Optional<MKVInfo> info = MKVInfoDeserializer.deserializeRawMKVInfo(null, resourceAsString);
		
		assertNotEquals(Optional.none(), info);
		assertEquals(11, info.getValue().getAttachments().size());
		
		Set<Integer> keyRing = expectedAttachmentMap.keySet();
		for(Integer index : keyRing){
			Attachment attachment = expectedAttachmentMap.get(index);
			assertMapEntry(info.getValue().getAttachments(), index, attachment);
		}
	}
	
	private static void assertMapEntry(List<Attachment> attachments, Integer index, Attachment attachment){
		int matches = 0;
		for(Attachment a : attachments){
			if(attachment.equals(a)){
				matches++;
			}
		}
		
		assertEquals(1, matches);
	}
}
