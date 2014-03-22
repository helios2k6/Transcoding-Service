package com.nlogneg.transcodingService.info.mkv;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A deserializer for the output produced by the mkvinfo tool that's a part of
 * the mkvtoolnix suite.
 * 
 * @author anjohnson
 * 
 */
public final class MKVInfoDeserializer
{

	private static final Logger Log = LogManager.getLogger(MKVInfoDeserializer.class);

	private static final String AttachedHeader = "+ ATTACHED";
	private static final String FileNameLabel = "+ FILE NAME:";
	private static final String MimeLabel = "+ MIME TYPE:";
	private static final String FileUIDLabel = "+ FILE UID:";

	/**
	 * Deserializes an MKVInfo from the given string, which is expected to be
	 * the actual raw input, not the name of a file
	 */
	public static Optional<MKVInfo> deserializeRawMKVInfo(
			final Path inputFile,
			final String rawMkvInfo)
	{
		final String[] splitString = rawMkvInfo.split("\\|");

		final List<Attachment> attachments = new ArrayList<Attachment>();

		long attachmentId = 0;
		Optional<String> fileName = Optional.none();
		Optional<MimeType> mimeType = Optional.none();
		Optional<Long> fileUID = Optional.none();

		for (final String item : splitString)
		{
			final String toUpper = item.toUpperCase().trim();

			// Find attachment header
			if (toUpper.contains(AttachedHeader))
			{
				// Increment counter and reset all references
				attachmentId++;
				fileName = Optional.none();
				mimeType = Optional.none();
				fileUID = Optional.none();
				continue;
			}

			// Found file name
			if (toUpper.contains(FileNameLabel))
			{
				fileName = tryParseDataFromTag(item);
			}

			// Found MIME type
			if (toUpper.contains(MimeLabel))
			{
				mimeType = tryParseMimeTypeFromTag(item);
			}

			if (toUpper.contains(FileUIDLabel))
			{
				fileUID = tryParseFileUID(item);
			}

			// Check to see if we can make a new Attachment
			if (fileName.isSome() && mimeType.isSome() && fileUID.isSome())
			{
				final Attachment attachment = new Attachment(
						attachmentId,
						fileName.getValue(),
						mimeType.getValue(),
						fileUID.getValue());
				attachments.add(attachment);

				// Clear out these references
				fileName = Optional.none();
				mimeType = Optional.none();
				fileUID = Optional.none();
			}
		}

		if (attachments.size() > 0)
		{
			return Optional.make(new MKVInfo(inputFile, attachments));
		}

		return Optional.none();
	}

	private static Optional<Long> tryParseFileUID(final String data)
	{
		final Optional<String> rawParsedString = tryParseDataFromTag(data);
		if (rawParsedString.isNone())
		{
			return Optional.none();
		}

		try
		{
			final Long fileUID = Long.parseLong(rawParsedString.getValue());
			return Optional.make(fileUID);
		} catch (final NumberFormatException e)
		{
			Log.error("Could not parse File UID.", e);
			return Optional.none();
		}
	}

	private static Optional<MimeType> tryParseMimeTypeFromTag(final String data)
	{
		final Optional<String> rawParsedString = tryParseDataFromTag(data);
		if (rawParsedString.isNone())
		{
			return Optional.none();
		}

		try
		{
			final MimeType mimeType = new MimeType(rawParsedString.getValue());
			return Optional.make(mimeType);
		} catch (final MimeTypeParseException e)
		{
			Log.error("Could not parse MIME type", e);
			return Optional.none();
		}
	}

	private static Optional<String> tryParseDataFromTag(final String data)
	{
		final String[] splitData = data.split(":");
		if (splitData.length == 2)
		{
			return Optional.make(splitData[1].trim());
		}
		return Optional.none();
	}
}
