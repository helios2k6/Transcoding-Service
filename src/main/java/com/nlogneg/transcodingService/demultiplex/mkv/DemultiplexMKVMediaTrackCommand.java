package com.nlogneg.transcodingService.demultiplex.mkv;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfo;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoProxy;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SystemUtilities;

public abstract class DemultiplexMKVMediaTrackCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(DemultiplexMKVMediaTrackCommand.class);
	private static final String TracksArgument = "tracks";
	
	public void execute(INotification notification){
		DemultiplexMKVJob mkvJob = (DemultiplexMKVJob)notification.getBody();
		Log.info("Starting demultiplexing MKV file: " + mkvJob);
		
		MediaInfoProxy mediaInfoProxy = (MediaInfoProxy)getFacade().retrieveProxy(MediaInfoProxy.PROXY_NAME);
		Optional<MediaInfo> mediaInfo = mediaInfoProxy.getMediaInfo(mkvJob.getMediaFile());

		if(mediaInfo.isNone()){
			Log.error("Could not get media info for file: " + mkvJob);
			return;
		}
		
		MediaTrack track = getTrackToDemultiplex(mediaInfo.getValue());
		String outputName = getOutputFileName(mkvJob.getMediaFile(), track);
		
		Log.info("Demultiplexing track " + track.getId() + "for file: " + mkvJob);
		
		StringBuilder argument = new StringBuilder();
		argument.append(track.getId()).append(":").append(outputName);
		
		ProcessBuilder builder = new ProcessBuilder(SystemUtilities.getMkvExtractProcessName(), TracksArgument, argument.toString());
		
		try{
			Process process = builder.start();
			process.waitFor();
			
			Facade facade = getFacade();
			ExtractedTracksProxy proxy = (ExtractedTracksProxy)facade.retrieveProxy(ExtractedTracksProxy.PROXY_NAME);
			proxy.put(mkvJob.getMediaFile(), track, outputName);
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
	 * @param filePath The file being demultiplexed
	 * @param mediaTrack The media track being extracted
	 * @return The file name to use for the demultiplexed track
	 */
	protected abstract String getOutputFileName(Path filePath, MediaTrack mediaTrack);
	
}