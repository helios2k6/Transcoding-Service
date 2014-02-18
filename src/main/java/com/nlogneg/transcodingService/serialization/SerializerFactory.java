package com.nlogneg.transcodingService.serialization;

import com.nlogneg.transcodingService.media.Compatibility;
import com.nlogneg.transcodingService.media.EncodingSettings;
import com.nlogneg.transcodingService.media.Estimation;
import com.nlogneg.transcodingService.media.Level;
import com.nlogneg.transcodingService.media.Profile;
import com.nlogneg.transcodingService.media.PsychoVisualSettings;
import com.nlogneg.transcodingService.media.RateControl;
import com.nlogneg.transcodingService.requests.Request;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * A factory that generates XStream serializers
 * @author anjohnson
 *
 */
public final class SerializerFactory {
	
	/**
	 * Generates the default XStream serializer
	 * @return
	 */
	public static final XStream generateDefaultXStreamSerializer(){
		XStream xstream = new XStream(new DomDriver());
		
		mapBaseXmlAliases(xstream);
		mapRateControlAliases(xstream);
		mapRateControlAliases(xstream);
		mapEstimationAliases(xstream);
		mapCompatibilityAliases(xstream);
		
		return xstream;
	}
	
	private static void mapBaseXmlAliases(XStream xstream){
		xstream.alias("request", Request.class);
		xstream.alias("encodingSettings", EncodingSettings.class);
		xstream.alias("rateControl", RateControl.class);
		xstream.alias("estimation", Estimation.class);
		xstream.alias("profile", Profile.class);
		xstream.alias("level", Level.class);
		xstream.alias("compatibility", Compatibility.class);
		xstream.alias("psychoVisualSettings", PsychoVisualSettings.class);
	}
	
	private static void mapRateControlAliases(XStream xstream){
		xstream.alias("type", RateControl.Type.class);
	}
	
	private static void mapEstimationAliases(XStream xstream){
		xstream.alias("motionEstimation", Estimation.MotionEstimation.class);
	}
	
	private static void mapCompatibilityAliases(XStream xstream){
		xstream.alias("sampleAspectRatio", Compatibility.SampleAspectRatio.class);
	}
}
