package com.nlogneg.transcodingService.info.mkv;

import java.nio.file.Path;

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
	
	private final MKVInfoQueryStrategy<Path, String> queryStrategy;
	
	/**
	 * The strategy to use for querying MKVInfo
	 * @param queryStrategy The strategy
	 */
	public QueryMKVInfoCommand(MKVInfoQueryStrategy<Path, String> queryStrategy){ 
		this.queryStrategy = queryStrategy;
	}

	public void execute(INotification notification){
		Path sourceFile = (Path)notification.getBody();
		Log.info("Querying MKVInfo for: " + sourceFile.toString());
		Optional<String> rawQuery = queryStrategy.queryForMKVInfo(sourceFile);

		if(rawQuery.isSome()){
			Optional<MKVInfo> info = MKVInfoDeserializer.deserializeRawMKVInfo(sourceFile, rawQuery.getValue());

			if(info.isSome()){
				MKVInfoProxy proxy = (MKVInfoProxy)getFacade().retrieveProxy(MKVInfoProxy.PROXY_NAME);
				proxy.put(info.getValue());
			}
		}
	}
}
