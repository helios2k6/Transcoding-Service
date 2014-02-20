package com.nlogneg.transcodingService.transcoding.mkv;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A strategy for querying for an MKV Info object
 * @author anjohnson
 *
 * @param <T> The type of input
 * @param <K> The type of output
 */
public interface MKVInfoQueryStrategy<T, K>{
	
	/**
	 * Query for an MKV Info
	 * @param sourceFile The source file to query, represented by a T
	 * @return An optional of type K
	 */
	Optional<K> queryForMKVInfo(T sourceFile);
}
