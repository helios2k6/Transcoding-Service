package com.nlogneg.transcodingService.demultiplex.mkv.attachments.fonts;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.demultiplex.mkv.attachments.ExtractMKVAttachmentsCommand;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.utilities.MimeTypeUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

public class ExtractMKVFontAttachmentsCommand extends ExtractMKVAttachmentsCommand{
	
	private static final Logger Log = LogManager.getLogger(ExtractMKVFontAttachmentsCommand.class);
		
	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.transcoding.mkv.demultiplex.ExtractMKVAttachmentsCommand#getAttachmentsToExtract(com.nlogneg.transcodingService.transcoding.mkv.MKVFileEncodingJob)
	 */
	@Override
	protected List<Attachment> getAttachmentsToExtract(DemultiplexMKVJob job){
		List<Attachment> attachments = new ArrayList<>();
		for(Attachment a : job.getAttachments()){
			MimeType attachmentMime = a.getMimeType();
			if(MimeTypeUtilities.areEqual(attachmentMime, getFontMimeType())){
				attachments.add(a);
			}
		}
		
		return attachments;
	}

	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.transcoding.mkv.demultiplex.ExtractMKVAttachmentsCommand#postProcessAttachmentExtraction()
	 */
	@Override
	protected void postProcessAttachmentExtraction(List<Attachment> extractedAttachments) {
		List<Path> paths = getPathsForAttachments(extractedAttachments);
		ExtractedFontAttachmentsProxy proxy = (ExtractedFontAttachmentsProxy)getFacade().retrieveProxy(ExtractedFontAttachmentsProxy.PROXY_NAME);
		proxy.add(paths);
	}

	private static final List<Path> getPathsForAttachments(List<Attachment> attachments){
		List<Path> paths = new ArrayList<>();
		for(Attachment attachment : attachments){
			Optional<Path> path = tryConvertStringToPath(attachment.getFileName());
			if(path.isSome()){
				paths.add(path.getValue());
			}
		}
		
		return paths;
	}
	
	private static final Optional<Path> tryConvertStringToPath(String file){
		try{
			Path path = Paths.get(file).toAbsolutePath();
			return Optional.make(path);
		}catch(Exception e){
			Log.error("Could not parse path: " + file, e);
		}
		
		return Optional.none();
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
