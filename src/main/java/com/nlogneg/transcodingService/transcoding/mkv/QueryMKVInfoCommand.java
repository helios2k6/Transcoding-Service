package com.nlogneg.transcodingService.transcoding.mkv;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class QueryMKVInfoCommand extends SimpleCommand{
	
	private static final Logger Log = LogManager.getLogger(QueryMKVInfoCommand.class);
	
	public void execute(INotification notification){
		String sourceFile = (String)notification.getBody();
		MKVInfoQuery query = new MKVInfoQuery(sourceFile);
		try {
			MKVInfo result = query.executeQuery();
			if(result != null){
				MKVInfoProxy proxy = (MKVInfoProxy)getFacade().retrieveProxy(MKVInfoProxy.PROXY_NAME);
				proxy.put(sourceFile, result);
			}
		} catch (IOException | InterruptedException e) {
			Log.error("Could not get MKV Info about: " + sourceFile);
			return;
		}
	}
}
