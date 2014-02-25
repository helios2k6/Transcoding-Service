package com.nlogneg.transcodingService.demultiplex.mkv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.configuration.ServerConfigurationProxy;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.utilities.MimeTypeUtilities;
import com.nlogneg.transcodingService.utilities.Optional;

public class ExtractMKVSubtitleAttachmentsCommand extends ExtractMKVAttachmentsCommand{
	
	private static final Logger Log = LogManager.getLogger(ExtractMKVSubtitleAttachmentsCommand.class);
		
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
		
		ServerConfigurationProxy proxy = (ServerConfigurationProxy)getFacade().retrieveProxy(ServerConfigurationProxy.PROXY_NAME);
		
		//TODO: EXECUTE FONT STRATEGY
	}

	private static final void tryMoveFile(Path fileToMove, Path destinationDirectory){
		Path destinationFile = destinationDirectory.resolve(fileToMove.getFileName());
		
		try {
			Files.move(fileToMove, destinationFile, StandardCopyOption.ATOMIC_MOVE);
		}catch (IOException e){
			Log.error("Could not move file: " + fileToMove, e);
		}
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
