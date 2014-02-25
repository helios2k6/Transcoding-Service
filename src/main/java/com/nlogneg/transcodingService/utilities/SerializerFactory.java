package com.nlogneg.transcodingService.utilities;

import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.File;
import com.nlogneg.transcodingService.info.mediainfo.FileSectionConverter;
import com.nlogneg.transcodingService.info.mediainfo.GeneralTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mediainfo.Track;
import com.nlogneg.transcodingService.info.mediainfo.VideoTrack;
import com.nlogneg.transcodingService.request.Compatibility;
import com.nlogneg.transcodingService.request.EncodingSettings;
import com.nlogneg.transcodingService.request.Estimation;
import com.nlogneg.transcodingService.request.Level;
import com.nlogneg.transcodingService.request.Profile;
import com.nlogneg.transcodingService.request.PsychoVisualSettings;
import com.nlogneg.transcodingService.request.RateControl;
import com.nlogneg.transcodingService.request.Request;
import com.nlogneg.transcodingService.request.SampleAspectRatio;
import com.nlogneg.transcodingService.request.Selector;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * A factory that generates XStream serializers
 * @author anjohnson
 *
 */
public final class SerializerFactory {
	
	/**
	 * Generates the default Request serializer
	 * @return The serializer
	 */
	public static final XStream generateDefaultRequestSerializer(){
		XStream xstream = new XStream(new DomDriver());

		mapRequestSerializerAliases(xstream);
		
		return xstream;
	}
	
	private static void mapRequestSerializerAliases(XStream xstream){
		xstream.alias("request", Request.class);
		xstream.alias("encodingSettings", EncodingSettings.class);
		xstream.alias("rateControl", RateControl.class);
		xstream.alias("estimation", Estimation.class);
		xstream.alias("profile", Profile.class);
		xstream.alias("level", Level.class);
		xstream.alias("compatibility", Compatibility.class);
		xstream.alias("psychoVisualSettings", PsychoVisualSettings.class);
		xstream.alias("type", RateControl.Type.class);
		xstream.alias("motionEstimation", Estimation.MotionEstimation.class);
		xstream.alias("sampleAspectRatio", SampleAspectRatio.class);
		xstream.alias("selector", Selector.class);
	}
	
	/**
	 * Generates the default MediaInfo serializer
	 * @return The serializer
	 */
	public static final XStream generateDefaultMediaInfoSerializer(){
		XStream xstream = new XStream(new DomDriver()){
			
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next){
				return createMapperWrapper(next);
			}
		};
		
		mapMediaInfoAliases(xstream);
		mapMediaInfoFieldAliases(xstream);
		mapMediaInfoImplicitCollections(xstream);
		mapMediaInfoDeserializerConverters(xstream);
		mapMediaInfoImmutableClasses(xstream);
		
		return xstream;
	}
	
	private static void mapMediaInfoAliases(XStream xstream){
		xstream.alias("Mediainfo", MediaInfo.class);
		xstream.alias("File", File.class);
	}
	private static void mapMediaInfoFieldAliases(XStream xstream){
		xstream.aliasField("File", MediaInfo.class, "file");
		
		xstream.aliasField("Format", Track.class, "format");
		
		xstream.aliasField("Complete_name", GeneralTrack.class, "completeName");
		
		xstream.aliasField("Codec_ID", MediaTrack.class, "codecID");
		xstream.aliasField("ID", MediaTrack.class, "id");
		
		xstream.aliasField("Channel_s_", AudioTrack.class, "channels");
		xstream.aliasField("Language", AudioTrack.class, "language");
		
		xstream.aliasField("Width", VideoTrack.class, "width");
		xstream.aliasField("Height", VideoTrack.class, "height");
		xstream.aliasField("Display_aspect_ratio", VideoTrack.class, "displayAspectRatio");
		xstream.aliasField("Frame_rate_mode", VideoTrack.class, "frameRateMode");
		xstream.aliasField("Frame_rate", VideoTrack.class, "frameRate");
		
		xstream.aliasField("Language", TextTrack.class, "language");
	}
	
	private static void mapMediaInfoImplicitCollections(XStream xstream){
		xstream.addImplicitCollection(File.class, "tracks");
	}
	
	private static void mapMediaInfoDeserializerConverters(XStream xstream){
		xstream.registerConverter(new FileSectionConverter());
	}
	
	private static void mapMediaInfoImmutableClasses(XStream xstream){
		xstream.addImmutableType(MediaInfo.class);
		xstream.addImmutableType(File.class);
		xstream.addImmutableType(Track.class);
		xstream.addImmutableType(GeneralTrack.class);
		xstream.addImmutableType(MediaTrack.class);
		xstream.addImmutableType(AudioTrack.class);
		xstream.addImmutableType(VideoTrack.class);
		xstream.addImmutableType(TextTrack.class);
	}
	
	private static MapperWrapper createMapperWrapper(MapperWrapper next){
		return new MapperWrapper(next){
			public boolean shouldSerializeMember(Class definedIn, String fieldName){
				if(definedIn == Object.class){
					return false;
				}
				
				return super.shouldSerializeMember(definedIn, fieldName);
			}
		}; 
	}
}
