package com.nlogneg.transcodingService.demultiplex.mkv;

import java.util.HashMap;
import java.util.Map;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.info.mediainfo.Track;

/**
 * Represents the mapping between encoding jobs to extracted tracks
 * @author Andrew
 *
 */
public class ExtractedTracksProxy extends Proxy{
	public static final String PROXY_NAME = "ExtractedTracksProxy";
	
	private final Map<String, Map<Track, String>> extractedTrackMap = new HashMap<String, Map<Track, String>>();
	
	public ExtractedTracksProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Place a mapping into the proxy
	 * @param job The MKV File
	 * @param track The track that was extracted
	 * @param fileName The file name
	 */
	public synchronized void put(String job, Track track, String fileName){
		Map<Track, String> existingMappings = extractedTrackMap.get(job);
		
		if(existingMappings == null){
			existingMappings = new HashMap<Track, String>();
			extractedTrackMap.put(job,  existingMappings);
		}
		
		existingMappings.put(track, fileName);
	}
	
	/**
	 * Remove a specific mapping
	 * @param job The encoding job
	 */
	public synchronized void removeMapping(String job){
		extractedTrackMap.remove(job);
	}
}
