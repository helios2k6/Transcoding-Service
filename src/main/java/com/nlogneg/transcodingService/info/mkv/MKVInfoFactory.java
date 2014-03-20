package com.nlogneg.transcodingService.info.mkv;

import java.nio.file.Path;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A factory used to query for MKV info
 * 
 * @author anjohnson
 * 
 */
public final class MKVInfoFactory
{
	private static final Logger Log = LogManager
			.getLogger(MKVInfoFactory.class);

	/**
	 * Query for MKVInfo
	 * 
	 * @param mediaFilePath
	 * @param source
	 * @return
	 */
	public static Optional<MKVInfo> tryGetMKVInfo(final Path mediaFilePath,
			final MKVInfoSource source)
	{
		Log.info("Querying for MKVInfo for: " + mediaFilePath);

		final Optional<String> rawQuery = source.tryGetMKVInfo(mediaFilePath);

		if (rawQuery.isSome())
		{
			Log.info("Successfully got MKVInfo for: " + mediaFilePath);
			return MKVInfoDeserializer.deserializeRawMKVInfo(mediaFilePath,
					rawQuery.getValue());
		}

		Log.info("Could not get MKVInfo for: " + mediaFilePath);
		return Optional.none();
	}

	/**
	 * Query for MKVInfo with the default MKVInfoSource
	 * 
	 * @param medaiFilePath
	 * @return
	 */
	public static Optional<MKVInfo> tryGetMKVInfo(final Path mediaFilePath)
	{
		return tryGetMKVInfo(mediaFilePath,
				ExternalProcessMKVInfoSource.getInstance());
	}
}
