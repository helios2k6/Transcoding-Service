package com.nlogneg.transcodingService.info.mediainfo;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents a MediaInfo deserialization strategy
 * @author anjohnson
 *
 * @param <T> The type of input
 */
public interface MediaInfoDeserializationStrategy<T>{
	
	/**
	 * Deserialize a T into a Media Info
	 * @param t The input
	 * @return A new MediaInfo
	 */
	Optional<MediaInfo> deserializeMediaInfo(T t);
}
