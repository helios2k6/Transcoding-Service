package com.nlogneg.transcodingService.mediaInfo;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.requests.Request;
import com.nlogneg.transcodingService.utilities.InputStreamUtilities;

/**
 * Queries for media information about a specific file
 * @author anjohnson
 *
 */
public class QueryMediaInfoCommand extends SimpleCommand{
	
	private static final Logger Log = LogManager.getLogger(QueryMediaInfoCommand.class);

	private static final String MediaInfoProcessName = "mediainfo";
	private static final String OutputArgument = "--output=XML";
	
	@Override
	public void execute(INotification notification){
		String sourceFile = (String)notification.getBody();
		
		ProcessBuilder builder = new ProcessBuilder(MediaInfoProcessName, OutputArgument, sourceFile);
		try {
			Log.info("Requesting media info about: " + sourceFile);
			Process process = builder.start();
			String output = InputStreamUtilities.readInputStreamToEnd(process.getInputStream());
			
			process.waitFor(); //Shouldn't take long. 
			
			Facade facade = getFacade();
			RawMediaInfoProxy rawMediaInfoProxy = (RawMediaInfoProxy)facade.retrieveProxy(RawMediaInfoProxy.PROXY_NAME);
			rawMediaInfoProxy.put(sourceFile, output);
		} catch (IOException e) {
			Log.error("Could not get the media info for a specific object", e);
			return;
		} catch (InterruptedException e) {
			Log.error("Media Info process was interrupted.", e);
			return;
		}
	}
	
}
