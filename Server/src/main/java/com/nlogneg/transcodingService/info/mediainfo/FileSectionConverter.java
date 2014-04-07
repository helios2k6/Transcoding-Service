package com.nlogneg.transcodingService.info.mediainfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Custom converter for the File XML section of the media info object
 * 
 * @author anjohnson
 * 
 */
public final class FileSectionConverter implements Converter
{
	private static final Logger Log = LogManager.getLogger(FileSectionConverter.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(final Class clazz)
	{
		return clazz == File.class;
	}

	@Override
	public void marshal(
			final Object arg0,
			final HierarchicalStreamWriter arg1,
			final MarshallingContext arg2)
	{

		throw new NotImplementedException();
	}

	@Override
	public Object unmarshal(
			final HierarchicalStreamReader reader,
			final UnmarshallingContext context)
	{
		final List<Track> tracks = new ArrayList<Track>();

		while (reader.hasMoreChildren())
		{
			reader.moveDown();
			final String currentNode = reader.getNodeName();

			if (currentNode.equalsIgnoreCase(SerializationConstants.TrackXmlName))
			{
				final Track track = deserializeTrack(reader, context, tracks);

				if (track != null)
				{
					tracks.add(track);
				}
				else
				{
					Log.error("Could not deserialize track");
				}
			}

			reader.moveUp();
		}

		return new File(tracks);
	}

	private static Track deserializeTrack(
			final HierarchicalStreamReader reader,
			final UnmarshallingContext context,
			final Object contextReferencePoint)
	{
		final String typeOfTrack = reader.getAttribute(SerializationConstants.TrackTypeAttributeName);

		if (typeOfTrack.equals(SerializationConstants.VideoTrackAttribute))
		{
			return (Track) context.convertAnother(
					contextReferencePoint,
					VideoTrack.class);
		}

		if (typeOfTrack.equals(SerializationConstants.AudioTrackAttribute))
		{
			return (Track) context.convertAnother(
					contextReferencePoint,
					AudioTrack.class);
		}

		if (typeOfTrack.equals(SerializationConstants.GeneralTrackAttribute))
		{
			return (Track) context.convertAnother(
					contextReferencePoint,
					GeneralTrack.class);
		}

		if (typeOfTrack.equals(SerializationConstants.TextTrackAttribute))
		{
			return (Track) context.convertAnother(
					contextReferencePoint,
					TextTrack.class);
		}

		return null;
	}
}
