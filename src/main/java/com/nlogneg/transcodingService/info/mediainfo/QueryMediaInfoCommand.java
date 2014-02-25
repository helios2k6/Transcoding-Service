package com.nlogneg.transcodingService.info.mediainfo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Queries for media information about a specific file
 * @author anjohnson
 *
 */
public class QueryMediaInfoCommand extends SimpleCommand{
	
	private static final Logger Log = LogManager.getLogger(QueryMediaInfoCommand.class);

	private final MediaInfoQueryStrategy<String, String> mediaInfoStrategy;
	private final MediaInfoDeserializationStrategy<String> deserializationStrategy;
	
	
	/**
	 * Constructs a new QueryMediaInfoCommand
	 * @param mediaInfoStrategy The query strategy
	 * @param deserializationStrategy The deserialization strategy
	 */
	public QueryMediaInfoCommand(
			MediaInfoQueryStrategy<String, String> mediaInfoStrategy,
			MediaInfoDeserializationStrategy<String> deserializationStrategy){
		this.mediaInfoStrategy = mediaInfoStrategy;
		this.deserializationStrategy = deserializationStrategy;
	}

	@Override
	public void execute(INotification notification){
		String sourceFile = (String)notification.getBody();
		
		Log.info("Querying media info on file: " + sourceFile);
		Optional<String> mediaInfoXml = mediaInfoStrategy.queryMediaInfo(sourceFile);
		
		if(mediaInfoXml.isNone()){
			Log.error("Could not query media info on file: " + sourceFile);
			return;
		}
		
		Optional<MediaInfo> mediaInfo = deserializationStrategy.deserializeMediaInfo(mediaInfoXml.getValue());
		
		if(mediaInfo.isNone()){
			Log.error("Could not deserialize media info XML for file: " + sourceFile);
			return;
		}
		
		Log.info("Successfully queried media info for file: " + sourceFile);
		MediaInfoProxy proxy = (MediaInfoProxy)getFacade().retrieveProxy(MediaInfoProxy.PROXY_NAME);
		proxy.putMediaInfo(sourceFile, mediaInfo.getValue());
	}
	
}
