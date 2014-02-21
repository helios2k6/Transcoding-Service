package com.nlogneg.transcodingService.transcoding.mkv;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.nlogneg.transcodingService.mediaInfo.MediaInfo;
import com.nlogneg.transcodingService.mediaInfo.MediaInfoProxy;
import com.nlogneg.transcodingService.mediaInfo.Track;
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
		
		
	}
	
	private static int getAudioTrackNumber(MediaInfo mediaInfo){
		List<Track> tracks = mediaInfo.getFile().getTracks();
		throw new NotImplementedException();
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
