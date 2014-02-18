package com.nlogneg.transcodingService.serialization;

import com.nlogneg.transcodingService.mediaInfo.AudioTrack;
import com.nlogneg.transcodingService.mediaInfo.GeneralTrack;
import com.nlogneg.transcodingService.mediaInfo.MediaTrack;
import com.nlogneg.transcodingService.mediaInfo.Track;
import com.nlogneg.transcodingService.mediaInfo.VideoTrack;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TrackConverter implements Converter{

	@Override
	public boolean canConvert(Class clazz) {
		return AudioTrack.class == clazz
				|| VideoTrack.class == clazz
				|| GeneralTrack.class == clazz
				|| MediaTrack.class == clazz
				|| Track.class == clazz;
	}

	@Override
	public void marshal(
			Object arg0, 
			HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0,
			UnmarshallingContext arg1) {
		return null;
	}

}
