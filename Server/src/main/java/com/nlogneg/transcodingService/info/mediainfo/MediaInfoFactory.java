package com.nlogneg.transcodingService.info.mediainfo;

import java.nio.file.Path;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Queries for media information about a specific file
 * 
 * @author anjohnson
 * 
 */
public final class MediaInfoFactory
{

	/**
	 * Attempt to get the media info for a particular media file using the media
	 * info source
	 * 
	 * @param mediaFilePath
	 * @param source
	 * @return
	 */
	public static Optional<MediaInfo> tryGetMediaInfo(
			final Path mediaFilePath,
			final MediaInfoSource source)
	{
		final Optional<String> info = source.tryGetMediaInfo(mediaFilePath);

		if (info.isNone())
		{
			return Optional.none();
		}

		return MediaInfoXmlDeserializer.deserializeMediaInfo(info.getValue());
	}

	/**
	 * Attempt to get the media info for a particular media file using the
	 * default media info source
	 * 
	 * @param mediaFilePath
	 * @return
	 */
	public static Optional<MediaInfo> tryGetMediaInfo(final Path mediaFilePath)
	{
		return tryGetMediaInfo(
				mediaFilePath,
				ExternalProcessMediaInfoSource.getInstance());
	}
}
