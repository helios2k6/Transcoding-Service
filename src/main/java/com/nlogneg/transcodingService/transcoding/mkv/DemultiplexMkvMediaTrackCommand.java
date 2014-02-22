package com.nlogneg.transcodingService.transcoding.mkv;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.mediaInfo.MediaInfo;
import com.nlogneg.transcodingService.mediaInfo.MediaInfoProxy;
import com.nlogneg.transcodingService.mediaInfo.MediaTrack;
import com.nlogneg.transcodingService.utilities.Optional;

public abstract class DemultiplexMkvMediaTrackCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(DemultiplexMkvMediaTrackCommand.class);
	private static final String TracksArgument = "tracks";
	
	public void execute(INotification notification){
		String file = (String)notification.getBody();
		Log.info("Starting demultiplexing MKV file: " + file);
		
		MediaInfoProxy mediaInfoProxy = (MediaInfoProxy)getFacade().retrieveProxy(MediaInfoProxy.PROXY_NAME);
		Optional<MediaInfo> mediaInfo = mediaInfoProxy.getMediaInfo(file);

		if(mediaInfo.isNone()){
			Log.error("Could not get media info for file: " + file);
			return;
		}
		
		MediaTrack track = getTrackToDemultiplex(mediaInfo.getValue());
		String getOutputName = getOutputFileName(file, track);
		
		Log.info("Demultiplexing track " + track.getId() + "for file: " + file);
		
		StringBuilder argument = new StringBuilder();
		argument.append(track.getId()).append(":").append(getOutputName);
		
		ProcessBuilder builder = new ProcessBuilder(getProcessName(), TracksArgument, argument.toString());
		
		try{
			Process process = builder.start();
			process.waitFor();
			//TODO: NEED TO RECORD WHERE THE FILE IS
		}catch (IOException e){
			Log.error("Could not start process for demultiplexing", e);
		}catch (InterruptedException e){
			Log.error("Thread interrupted from waiting for process to end.");
		}
	}
	
	/**
	 * Get the track to demultiplex
	 * @param mediaInfo The media info
	 * @return The Media Track to demultiplex
	 */
	protected abstract MediaTrack getTrackToDemultiplex(MediaInfo mediaInfo);
	
	/**
	 * Get the file name to use for the demuliplexed track
	 * @param fileName The file being demultiplexed
	 * @param mediaTrack The media track being extracted
	 * @return The file name to use for the demultiplexed track
	 */
	protected abstract String getOutputFileName(String fileName, MediaTrack mediaTrack);
	
	/**
	 * Gets the process name based on the operating sytem
	 * @return The expected mkvinfo process name
	 */
	private static String getProcessName(){
		String osProperty = System.getProperty("os.name");
		if(osProperty.contains("Windows")){
			return "mkvextract.exe";
		}

		return "mkvextract";
	}
}
