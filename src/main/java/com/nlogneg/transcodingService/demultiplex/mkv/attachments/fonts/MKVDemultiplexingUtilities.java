package com.nlogneg.transcodingService.demultiplex.mkv.attachments.fonts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.activation.MimeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.utilities.MimeTypeUtilities;

/**
 * Utility class for demultiplexing MKV Files
 * @author anjohnson
 *
 */
public final class MKVDemultiplexingUtilities{
	private static final Logger Log = LogManager.getLogger(MKVDemultiplexingUtilities.class);
	private static final MimeType FontMimeType = getFontMimeType();
	
	/**
	 * Filters the collection for attachments that are fonts
	 * @param attachments
	 * @return
	 */
	public static Collection<Attachment> getFontAttachments(Collection<Attachment> attachments){
		Set<Attachment> result = new HashSet<Attachment>();
		for(Attachment a : attachments){
			MimeType mime = a.getMimeType();
			if(MimeTypeUtilities.areEqual(mime, FontMimeType)){
				result.add(a);
			}
		}
		
		return result;
	}
	
	private static final MimeType getFontMimeType(){
		try{
			return new MimeType("application/x-truetype-font");
		}catch(Exception e){
			//Can't get here, because we know that this works
			Log.error("We made the impossible, possible.", e);
			return null;
		}
	}
}
