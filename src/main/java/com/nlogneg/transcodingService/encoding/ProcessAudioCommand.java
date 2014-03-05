package com.nlogneg.transcodingService.encoding;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.demultiplex.ExtractedTracksProxy;

/**
 * Processes the audio track of a given EncodingJob
 * @author anjohnson
 *
 */
public class ProcessAudioCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(ProcessAudioCommand.class);
	public void execute(INotification notification){
		EncodingJob job = (EncodingJob)notification.getBody();
		AudioTrackOption audioOption = job.getAudioTrackOption();
		
		//Check to see what we do with the audio track
		if(audioOption.getEncodingAction() != EncodingAction.Encode){
			return;
		}
		
		Log.info("Encoding audio track");
		encodeAudioTrack(job);
	}
	
	private void encodeAudioTrack(EncodingJob job){
		ExtractedTracksProxy extractedTracksProxy = (ExtractedTracksProxy)getFacade().retrieveProxy(ExtractedTracksProxy.PROXY_NAME);
		//TODO: finish this part
	}
}
