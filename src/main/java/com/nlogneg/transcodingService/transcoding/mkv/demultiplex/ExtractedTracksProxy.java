package com.nlogneg.transcodingService.transcoding.mkv.demultiplex;

import java.util.HashMap;
import java.util.Map;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.mediaInfo.Track;
import com.nlogneg.transcodingService.transcoding.EncodingJob;

/**
 * Represents the mapping between encoding jobs to extracted tracks
 * @author Andrew
 *
 */
public class ExtractedTracksProxy extends Proxy{
	public static final String PROXY_NAME = "ExtractedTracksProxy";
	
	private final Map<EncodingJob, Map<Track, String>> extractedTrackMap = new HashMap<EncodingJob, Map<Track, String>>();
	
	public ExtractedTracksProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Place a mapping into the proxy
	 * @param job The encoding job
	 * @param track The track that was extracted
	 * @param fileName The file name
	 */
	public synchronized void put(EncodingJob job, Track track, String fileName){
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
	public synchronized void removeMapping(EncodingJob job){
		extractedTrackMap.remove(job);
	}
}
