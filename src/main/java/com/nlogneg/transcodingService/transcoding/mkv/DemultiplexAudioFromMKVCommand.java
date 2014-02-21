package com.nlogneg.transcodingService.transcoding.mkv;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.mediaInfo.AudioTrack;
import com.nlogneg.transcodingService.mediaInfo.MediaInfo;
import com.nlogneg.transcodingService.mediaInfo.MediaInfoProxy;
import com.nlogneg.transcodingService.mediaInfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.mediaInfo.MediaInfoTrackSummaryFactory;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Demultiplexes streams and attachments from MKV files
 * @author anjohnson
 *
 */
public class DemultiplexAudioFromMKVCommand extends SimpleCommand{
	private static final Logger Log = LogManager.getLogger(DemultiplexAudioFromMKVCommand.class);

	public void execute(INotification notification){
		String file = (String)notification.getBody();

		Log.info("Demultiplexing audio from MKV: " + file);

		MediaInfoProxy mediaInfoProxy = (MediaInfoProxy)getFacade().retrieveProxy(MediaInfoProxy.PROXY_NAME);
		Optional<MediaInfo> mediaInfo = mediaInfoProxy.getMediaInfo(file);

		if(mediaInfo.isNone()){
			Log.error("Could not get media info for file: " + file);
			return;
		}
		
		int audioTrackId = getAudioTrackNumber(mediaInfo.getValue());
		if(audioTrackId == -1){
			Log.info("No suitable audio track could be found for file: " + file);
			return;
		}
		
		
	}

	/**
	 * Attempts to deduce the "best" audio track to use given the media info.
	 * 
	 * The rules of selecting an appropriate audio track are as follows:
	 * - Japanese tracks take highest priority
	 * - The track with the smallest ID number is preferred
	 * 
	 * @param mediaInfo The media info
	 * @return which track to use or -1 if it couldn't find any
	 */
	private static int getAudioTrackNumber(MediaInfo mediaInfo){
		MediaInfoTrackSummary summary = MediaInfoTrackSummaryFactory.getSummary(mediaInfo);

		Set<AudioTrack> audioTracks = summary.getAudioTracks();
		AudioTrack selectedAudioTrack = null;

		for(AudioTrack track : audioTracks){
			if(selectedAudioTrack == null){
				//Initial track
				selectedAudioTrack = track;
			}else{
				//See if the selected track is Japanese
				if(isTrackJapanese(selectedAudioTrack)){
					/*
					 * If so, then the only way we'll unseat the previous
					 * selection is if the track ID is lower
					 */
					if(track.getId() < selectedAudioTrack.getId()){
						selectedAudioTrack = track;
					}
				}else{
					/*
					 * OK well now there's two ways to unseat the previous track:
					 * 
					 * 1. The candidate track is Japanese
					 * 2. The candidate track has a lower ID number 
					 */
					
					if(isTrackJapanese(track) || track.getId() < selectedAudioTrack.getId()){
						selectedAudioTrack = track;
					}
				}
			}
		}

		if(selectedAudioTrack != null){
			return selectedAudioTrack.getId();
		}

		return -1;
	}

	private static boolean isTrackJapanese(AudioTrack track){
		String language = track.getLanguage();
		if(language != null){
			return language.equalsIgnoreCase("Japanese");
		}

		return false;
	}

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
