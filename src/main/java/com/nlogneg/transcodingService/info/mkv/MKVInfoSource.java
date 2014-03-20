package com.nlogneg.transcodingService.info.mkv;

import java.nio.file.Path;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * An object that can be used to query for MKV info
 * @author anjohnson
 *
 */
public interface MKVInfoSource{
	/**
	 * Try to get the MKV Info of the media file path
	 * @param mediaFilePath The media file
	 * @return The MKV info
	 */
	public Optional<String> tryGetMKVInfo(Path mediaFilePath);
}
