package com.nlogneg.transcodingService.demultiplex.mkv.attachments;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.fonts.FontInstaller;
import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Extracts the attachments of an MKV 
 * @author Andrew
 *
 */
public final class ProcessAttachmentRunnable implements Runnable{
	private static final Logger Log = LogManager.getLogger(ProcessAttachmentRunnable.class);
	private static final String AttachmentArgument = "attachments";
	
	private final DemultiplexMKVJob job;
	private final CompletionHandler<Void, DemultiplexMKVJob> asyncCallback;
	private final FontInstaller fontInstaller;
	private final Path fontFolder;

	/**
	 * @param job
	 * @param asyncCallback
	 * @param fontInstaller
	 * @param fontFolder
	 */
	public ProcessAttachmentRunnable(
			DemultiplexMKVJob job,
			CompletionHandler<Void, DemultiplexMKVJob> asyncCallback,
			FontInstaller fontInstaller, 
			Path fontFolder){
		this.job = job;
		this.asyncCallback = asyncCallback;
		this.fontInstaller = fontInstaller;
		this.fontFolder = fontFolder;
	}

	@Override
	public void run(){
		boolean extractionResult = extractAttachments(job);
		boolean installationResult = fontInstaller.installFonts(job.getFontAttachmentMap().values(), fontFolder);
		
		if(extractionResult && installationResult){
			asyncCallback.completed(null, job);
		}else{
			asyncCallback.failed(null, job);
		}
	}

	private static boolean extractAttachments(DemultiplexMKVJob job){
		Map<Attachment, Path> attachments = job.getAttachmentToOutputMap();
		Set<Attachment> keyRing = attachments.keySet();
		boolean successfullyExtractedAllAttachments = true;
		
		for(Attachment key : keyRing){
			Path outputPath = attachments.get(key);
			boolean extractionResult = extractAttachment(job, key, outputPath);
			if(extractionResult){
				Log.info("Successfully extracted font: " + outputPath);
			}else{
				Log.info("Failed to extract font: " + outputPath);
				successfullyExtractedAllAttachments = false;
			}
		}
		
		return successfullyExtractedAllAttachments;
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
}
