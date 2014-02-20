package com.nlogneg.transcodingService.transcoding.mkv;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Queries a file for its MKVInfo 
 * @author anjohnson
 *
 */
public class QueryMKVInfoCommand extends SimpleCommand{
	
	private static final Logger Log = LogManager.getLogger(QueryMKVInfoCommand.class);
	
	private final MKVInfoQueryStrategy<String, String> queryStrategy;
	private final RawMKVInfoDeserializationStrategy<String> deserializationStrategy;
	
	/**
	 * The strategy to use for querying MKVInfo
	 * @param queryStrategy The strategy
	 */
	public QueryMKVInfoCommand(MKVInfoQueryStrategy<String, String> queryStrategy, 
			RawMKVInfoDeserializationStrategy<String> deserializationStrategy){
		this.queryStrategy = queryStrategy;
		this.deserializationStrategy = deserializationStrategy;
	}

	public void execute(INotification notification){
		String sourceFile = (String)notification.getBody();
		Log.info("Querying MKVInfo for: " + sourceFile);
		Optional<String> rawQuery = queryStrategy.queryForMKVInfo(sourceFile);

		if(rawQuery.isSome()){
			Optional<MKVInfo> info = deserializationStrategy.deserializeRawMKVInfo(rawQuery.getValue());

			if(info.isSome()){
				MKVInfoProxy proxy = (MKVInfoProxy)getFacade().retrieveProxy(MKVInfoProxy.PROXY_NAME);
				proxy.put(sourceFile, info.getValue());
			}
		}
	}
}
