package com.nlogneg.transcodingService.info.mediainfo;

import java.nio.file.Path;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A source of media info
 * @author anjohnson
 *
 */
public interface MediaInfoSource{
	/**
	 * Try to query for the media information from a media file
	 * @param path The path
	 * @return The media info
	 */
	public Optional<String> tryGetMediaInfo(Path path);
}
