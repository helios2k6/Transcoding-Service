package com.nlogneg.transcodingService.demultiplex.mkv.tracks;

import java.nio.file.Path;
import java.nio.file.Paths;
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
	 * @param filePath the path to the MKV file
	 * @param track The track that was extracted
	 * @param fileName The file name
	 */
	public synchronized void put(Path filePath, Track track, String fileName){
		Map<Track, String> existingMappings = extractedTrackMap.get(filePath.toAbsolutePath().toString());
		
		if(existingMappings == null){
			existingMappings = new HashMap<Track, String>();
			extractedTrackMap.put(filePath.toAbsolutePath().toString(),  existingMappings);
		}
		
		existingMappings.put(track, fileName);
	}
	
	/**
	 * Get the extracted tracks for a particular media file
	 * @param mediaFile The media file
	 * @return The extracted track map
	 */
	public synchronized Map<Track, Path> getExtractedTracks(Path mediaFile){
		Map<Track, String> resultMap = new HashMap<Track, String>();
		resultMap = extractedTrackMap.get(mediaFile.toAbsolutePath().toString());
		
		Map<Track, Path> finalResultMap = new HashMap<Track, Path>();
		for(Track t : resultMap.keySet()){
			String result = resultMap.get(t);
			finalResultMap.put(t, Paths.get(result).toAbsolutePath());
		}
		
		return finalResultMap;
	}
	
	/**
	 * Remove a specific mapping
	 * @param filePath The MKV file
	 */
	public synchronized void removeMapping(Path filePath){
		extractedTrackMap.remove(filePath.toAbsolutePath().toString());
	}
}
