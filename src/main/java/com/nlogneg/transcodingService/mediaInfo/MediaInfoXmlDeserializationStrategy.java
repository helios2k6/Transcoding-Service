package com.nlogneg.transcodingService.mediaInfo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.serialization.SerializerFactory;
import com.nlogneg.transcodingService.utilities.Optional;
import com.thoughtworks.xstream.XStream;

/**
 * A MediaInfo deserialization strategy that can deserialize XML input
 * @author anjohnson
 *
 */
public class MediaInfoXmlDeserializationStrategy implements MediaInfoDeserializationStrategy<String>{

	private static final Logger Log = LogManager.getLogger(MediaInfoXmlDeserializationStrategy.class);
	
	private final XStream xstream;
	
	/**
	 * Constructs a new MediaInfoXmlDeserializationStrategy
	 * @param xstream The deserializer to use
	 */
	public MediaInfoXmlDeserializationStrategy(XStream xstream){
		this.xstream = xstream;
	}
	
	/**
	 * Constructs the default MediaInfoXmlDeserializationStrategy
	 */
	public MediaInfoXmlDeserializationStrategy(){
		xstream = SerializerFactory.generateDefaultMediaInfoSerializer();
	}
	
	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.mediaInfo.MediaInfoDeserializationStrategy#deserializeMediaInfo(java.lang.Object)
	 */
	@Override
	public Optional<MediaInfo> deserializeMediaInfo(String xmlInput){
		try{
			Log.info("Deserializing media info: " + xmlInput);
			
			MediaInfo info = (MediaInfo)xstream.fromXML(xmlInput);
			return Optional.make(info);
		}catch(Exception ex){
			Log.error("An error occurred while deserializing a MediaInfo object from XML.", ex);
		}
		
		return Optional.none();
	}

}
