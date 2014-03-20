package com.nlogneg.transcodingService.demultiplex.mkv.attachments;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.fonts.FontInstaller;
import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Extracts the attachments of an MKV
 * 
 * @author Andrew
 * 
 */
public final class ProcessAttachmentRunnable implements Runnable
{
	private static final Logger Log = LogManager
			.getLogger(ProcessAttachmentRunnable.class);
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
	public ProcessAttachmentRunnable(final DemultiplexMKVJob job,
			final CompletionHandler<Void, DemultiplexMKVJob> asyncCallback,
			final FontInstaller fontInstaller, final Path fontFolder)
	{
		this.job = job;
		this.asyncCallback = asyncCallback;
		this.fontInstaller = fontInstaller;
		this.fontFolder = fontFolder;
	}

	@Override
	public void run()
	{
		final boolean extractionResult = extractAttachments(this.job);
		final boolean installationResult = this.fontInstaller.installFonts(
				this.job.getFontAttachmentMap().values(), this.fontFolder);

		if (extractionResult && installationResult)
		{
			this.asyncCallback.completed(null, this.job);
		} else
		{
			this.asyncCallback.failed(null, this.job);
		}
	}

	private static boolean extractAttachments(final DemultiplexMKVJob job)
	{
		final Map<Attachment, Path> attachments = job.getAttachmentMap();
		final Set<Attachment> keyRing = attachments.keySet();
		boolean successfullyExtractedAllAttachments = true;

		for (final Attachment key : keyRing)
		{
			final Path outputPath = attachments.get(key);
			final boolean extractionResult = extractAttachment(job, key,
					outputPath);
			if (extractionResult)
			{
				Log.info("Successfully extracted font: " + outputPath);
			} else
			{
				Log.info("Failed to extract font: " + outputPath);
				successfullyExtractedAllAttachments = false;
			}
		}

		return successfullyExtractedAllAttachments;
	}

	private static boolean extractAttachment(final DemultiplexMKVJob job,
			final Attachment attachment, final Path outputPath)
	{
		final StringBuilder extractedAttachmentArgument = new StringBuilder();
		extractedAttachmentArgument.append(attachment.getId()).append(":")
				.append(attachment.getFileName());

		final ProcessBuilder builder = new ProcessBuilder(
				SystemUtilities.getMkvExtractProcessName(), AttachmentArgument,
				job.getMediaFile().toAbsolutePath().toString(),
				extractedAttachmentArgument.toString());

		final Optional<Process> processOptional = ProcessUtils
				.tryStartProcess(builder);
		if (processOptional.isNone())
		{
			Log.error("Could not start process for attachment extraction");
			return false;
		}

		return ProcessUtils.tryWaitForProcess(processOptional.getValue());
	}
}
