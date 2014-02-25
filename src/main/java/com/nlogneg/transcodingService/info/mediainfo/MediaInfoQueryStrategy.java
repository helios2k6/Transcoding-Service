package com.nlogneg.transcodingService.info.mediainfo;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A strategy for querying for Media Info
 * @author anjohnson
 *
 * @param <T> The type of input
 * @param <K> The type of output
 */
public interface MediaInfoQueryStrategy<T, K> {
	
	/**
	 * Query for media info
	 * @param t The input
	 * @return An Optional K representing the MediaInfo
	 */
	Optional<K> queryMediaInfo(T t);
}
