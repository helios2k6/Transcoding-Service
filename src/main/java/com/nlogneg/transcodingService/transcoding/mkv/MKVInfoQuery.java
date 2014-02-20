package com.nlogneg.transcodingService.transcoding.mkv;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.nlogneg.transcodingService.utilities.InputStreamUtilities;

/**
 * Represents the query for MKV information
 * @author Andrew
 *
 */
public final class MKVInfoQuery implements Runnable{
	
	private static final Logger Log = LogManager.getLogger(MKVInfoQuery.class);
	
	private static final String MKVInfoProcessName = "mkvinfo";
	private static final String TrackArgument = "--track-info";
	
	private final String sourceFile;
	private MKVInfo result;
	private boolean alreadyQueried;
	
	/**
	 * Constructs a new MKVInfoQuery
	 * @param sourceFile The source file to query information about
	 */
	public MKVInfoQuery(String sourceFile){
		this.sourceFile = sourceFile;
		this.alreadyQueried = false;
	}

	@Override
	public void run(){
		try {
			result = executeQuery();
		}catch (IOException | InterruptedException e){
			result = null;
			Log.error("Could not get result of MKVInfoQuery.", e);
		}
	}
	
	/**
	 * Execute this query and get the result immediately
	 * @return The result of this query
	 * @throws IOException If the external process throws 
	 * @throws InterruptedException If this thread is interrupted 
	 */
	public MKVInfo executeQuery() throws IOException, InterruptedException{
		if(alreadyQueried){
			return result;
		}
		
		alreadyQueried = true;
		
		String intermediateOutput = queryForRawResult(sourceFile);
		return parseRawOutput(intermediateOutput);
	}
	
	private static String queryForRawResult(String sourceFile) throws IOException, InterruptedException{
		Log.info("Requesting MKV info about: " + sourceFile);
		
		ProcessBuilder builder = new ProcessBuilder(MKVInfoProcessName, TrackArgument, sourceFile);
		Process process = builder.start();
		
		String output = InputStreamUtilities.readInputStreamToEnd(process.getInputStream());
		
		process.waitFor();
		
		return output;
	}
	
	private static MKVInfo parseRawOutput(String rawMKVInfo){
		throw new NotImplementedException();
	}
}
