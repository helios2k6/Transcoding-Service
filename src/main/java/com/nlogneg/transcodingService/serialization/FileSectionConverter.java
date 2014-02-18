package com.nlogneg.transcodingService.serialization;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.nlogneg.transcodingService.mediaInfo.AudioTrack;
import com.nlogneg.transcodingService.mediaInfo.File;
import com.nlogneg.transcodingService.mediaInfo.GeneralTrack;
import com.nlogneg.transcodingService.mediaInfo.SerializationConstants;
import com.nlogneg.transcodingService.mediaInfo.TextTrack;
import com.nlogneg.transcodingService.mediaInfo.Track;
import com.nlogneg.transcodingService.mediaInfo.VideoTrack;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Custom converter for the File XML section of the media info object
 * @author anjohnson
 *
 */
public class FileSectionConverter implements Converter{
	private static final Logger Log = LogManager.getLogger(FileSectionConverter.class);
	
	@Override
	public boolean canConvert(Class clazz) {
		return clazz == File.class;
	}

	@Override
	public void marshal(
			Object arg0, 
			HierarchicalStreamWriter arg1,
			MarshallingContext arg2){
		
		throw new NotImplementedException();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context){
		List<Track> tracks = new ArrayList<Track>();
		
		while(reader.hasMoreChildren()){
			reader.moveDown();
			String currentNode = reader.getNodeName();
			
			if(currentNode.equalsIgnoreCase(SerializationConstants.TrackXmlName)){
				Track track = deserializeTrack(reader, context, tracks);
				
				if(track != null){
					tracks.add(track);
				}else{
					Log.error("Could not deserialize track");
				}
			}
			
			reader.moveUp();
		}
		
		return new File(tracks);
	}
	
	private static Track deserializeTrack(HierarchicalStreamReader reader, UnmarshallingContext context, Object contextReferencePoint){
		String typeOfTrack = reader.getAttribute(SerializationConstants.TrackTypeAttributeName);
		
		if(typeOfTrack.equals(SerializationConstants.VideoTrackAttribute)){
			return (Track)context.convertAnother(contextReferencePoint, VideoTrack.class);
		}
		
		if(typeOfTrack.equals(SerializationConstants.AudioTrackAttribute)){
			return (Track)context.convertAnother(contextReferencePoint, AudioTrack.class);
		}
		
		if(typeOfTrack.equals(SerializationConstants.GeneralTrackAttribute)){
			return (Track)context.convertAnother(contextReferencePoint, GeneralTrack.class);
		}
		
		if(typeOfTrack.equals(SerializationConstants.TextTrackAttribute)){
			return (Track)context.convertAnother(contextReferencePoint, TextTrack.class);
		}
		
		return null;
	}
}
