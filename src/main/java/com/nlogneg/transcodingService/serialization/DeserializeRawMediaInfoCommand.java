package com.nlogneg.transcodingService.serialization;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.mediaInfo.MediaInfo;
import com.nlogneg.transcodingService.mediaInfo.MediaInfoProxy;
import com.thoughtworks.xstream.XStream;

/**
 * Deserializes a raw media info object to a media info object
 * @author anjohnson
 *
 */
public class DeserializeRawMediaInfoCommand extends SimpleCommand{
	
	/**
	 * An internal media deserialization request
	 * @author anjohnson
	 *
	 */
	public class RawMediaInfoRequest{
		private final String mediaInfo;
		private final String filePath;
		
		/**
		 * @param mediaInfo
		 * @param filePath
		 */
		public RawMediaInfoRequest(String mediaInfo, String filePath) {
			super();
			this.mediaInfo = mediaInfo;
			this.filePath = filePath;
		}

		/**
		 * @return the mediaInfo
		 */
		public String getMediaInfo() {
			return mediaInfo;
		}

		/**
		 * @return the filePath
		 */
		public String getFilePath() {
			return filePath;
		}
	}
	
	private final XStream deserializer;
	
	public DeserializeRawMediaInfoCommand(XStream deserializer){
		this.deserializer = deserializer;
	}
	
	public DeserializeRawMediaInfoCommand(){
		this.deserializer = SerializerFactory.generateDefaultMediaInfoSerializer();
	}
	
	public void execute(INotification notification){
		RawMediaInfoRequest request = (RawMediaInfoRequest)notification.getBody();
		String rawMediaInfo = request.getMediaInfo();
		
		MediaInfo info = (MediaInfo)deserializer.fromXML(rawMediaInfo);
		
		Facade facade = getFacade();
		
		MediaInfoProxy mediaInfoProxy = (MediaInfoProxy)facade.retrieveProxy(MediaInfoProxy.PROXY_NAME);
		mediaInfoProxy.putMediaInfo(request.getFilePath(), info);
	}
}
