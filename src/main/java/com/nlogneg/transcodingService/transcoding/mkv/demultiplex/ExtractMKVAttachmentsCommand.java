package com.nlogneg.transcodingService.transcoding.mkv.demultiplex;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.transcoding.mkv.Attachment;
import com.nlogneg.transcodingService.transcoding.mkv.MKVFileEncodingJob;
import com.nlogneg.transcodingService.utilities.SystemUtilities;

/**
 * Represents the base class for extracting MKV attachments 
 * @author anjohnson
 *
 */
public abstract class ExtractMKVAttachmentsCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(ExtractMKVAttachmentsCommand.class);
	private static final String AttachmentArgument = "attachments";
	
	public final void execute(INotification notification){
		MKVFileEncodingJob job = (MKVFileEncodingJob)notification.getBody();
		
		List<Attachment> attachments = getAttachmentsToExtract(job);
		
		for(Attachment attachment : attachments){
			extractAttachment(job, attachment);
		}
		
		postProcessAttachmentExtraction(attachments);
	}
	
	/**
	 * Get the attachments that we want to extract
	 * @param job The MKV Encoding Job
	 * @return The list of attachments
	 */
	protected abstract List<Attachment> getAttachmentsToExtract(MKVFileEncodingJob job);
	
	/**
	 * Perform any post processing 
	 */
	protected abstract void postProcessAttachmentExtraction(List<Attachment> extractedAttachments);
	
	private static void extractAttachment(MKVFileEncodingJob job, Attachment attachment){
		Log.info("Extracting attachments for: " + job.getFile());
		
		StringBuilder extractedAttachmentArgument = new StringBuilder();
		extractedAttachmentArgument.append(attachment.getId()).append(":").append(attachment.getFileName());
		
		ProcessBuilder builder = new ProcessBuilder(
				SystemUtilities.getMkvExtractProcessName(),
				AttachmentArgument,
				job.getFile(),
				extractedAttachmentArgument.toString());
		
		try{
			Process process = builder.start();
			process.waitFor();
		}catch (IOException e){
			Log.error("An error occurred while extracting attachments.", e);
			return;
		}catch (InterruptedException e){
			Log.error("This thread was interrupted while waiting for an external process to end.", e);
			return;
		}
	}
}