package com.nlogneg.transcodingService.transcoding.mkv;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * A deserializer for the output produced by the mkvinfo tool that's a part of the 
 * mkvtoolnix suite
 * @author anjohnson
 *
 */
public class MKVToolNixRawMKVInfoDeserializerStrategy implements RawMKVInfoDeserializationStrategy<String>{

	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.transcoding.mkv.RawMKVInfoDeserializationStrategy#deserializeRawMKVInfo(java.lang.Object)
	 */
	@Override
	public Optional<MKVInfo> deserializeRawMKVInfo(String t) {
		// TODO Auto-generated method stub
		return Optional.none();
	}

}
