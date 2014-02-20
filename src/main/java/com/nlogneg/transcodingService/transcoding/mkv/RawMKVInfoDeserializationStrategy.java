package com.nlogneg.transcodingService.transcoding.mkv;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A strategy for deserializing a raw MKVInfo object into an actual MKVInfo object
 * @author anjohnson
 *
 * @param <T> The type of the raw input
 */
public interface RawMKVInfoDeserializationStrategy<T>{
	/**
	 * Deserialize the raw input
	 * @param t The input
	 * @return The optional representing the deserialized MKVInfo
	 */
	Optional<MKVInfo> deserializeRawMKVInfo(T t);
}
