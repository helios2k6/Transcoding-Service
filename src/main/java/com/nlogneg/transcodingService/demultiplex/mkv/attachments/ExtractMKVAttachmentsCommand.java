package com.nlogneg.transcodingService.demultiplex.mkv.attachments;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.demultiplex.mkv.attachments.fonts.ExtractedFontAttachmentsProxy;
import com.nlogneg.transcodingService.demultiplex.mkv.attachments.fonts.MKVDemultiplexingUtilities;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Represents the base class for extracting MKV attachments 
 * @author anjohnson
 *
 */
public final class ExtractMKVAttachmentsCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(ExtractMKVAttachmentsCommand.class);
	private static final String AttachmentArgument = "attachments";
	
	public final void execute(INotification notification){
		DemultiplexMKVJob job = (DemultiplexMKVJob)notification.getBody();
		
		Map<Attachment, Path> extractedAttachments = extractAttachments(job);
		addFontAttachmentsToProxy(getFacade(), extractedAttachments);
	}
	
	private static Map<Attachment, Path> extractAttachments(DemultiplexMKVJob job){
		Map<Attachment, Path> attachments = job.getAttachmentToOutputMap();
		Set<Attachment> keyRing = attachments.keySet();
		Map<Attachment, Path> result = new HashMap<>();
		
		for(Attachment key : keyRing){
			Path outputPath = attachments.get(key);
			boolean extractResult = extractAttachment(job, key, outputPath);
			if(extractResult){
				result.put(key, outputPath);
			}
		}
		
		return result;
	}
	
	private static boolean extractAttachment(DemultiplexMKVJob job, Attachment attachment, Path outputPath){
		StringBuilder extractedAttachmentArgument = new StringBuilder();
		extractedAttachmentArgument.append(attachment.getId()).append(":").append(attachment.getFileName());
		
		ProcessBuilder builder = new ProcessBuilder(
				SystemUtilities.getMkvExtractProcessName(),
				AttachmentArgument,
				job.getMediaFile().toAbsolutePath().toString(),
				extractedAttachmentArgument.toString());
		
		try{
			Process process = builder.start();
			process.waitFor();
			return true;
		}catch (IOException e){
			Log.error("An error occurred while extracting attachments.", e);
		}catch (InterruptedException e){
			Log.error("This thread was interrupted while waiting for an external process to end.", e);
		}
		
		return false;
	}
	
	private static void addFontAttachmentsToProxy(Facade facade, Map<Attachment, Path> extractedAttachments){
		Collection<Attachment> fontAttachments = MKVDemultiplexingUtilities.getFontAttachments(extractedAttachments.keySet());
		Set<Path> fontPaths = new HashSet<>();
		
		for(Attachment a : fontAttachments){
			fontPaths.add(extractedAttachments.get(a));

		}
		ExtractedFontAttachmentsProxy proxy = (ExtractedFontAttachmentsProxy)facade.retrieveProxy(ExtractedFontAttachmentsProxy.PROXY_NAME);
		proxy.add(fontPaths);
	}
}