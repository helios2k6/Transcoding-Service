package com.nlogneg.transcodingService.demultiplex.mkv.tracks;

import java.nio.file.Path;
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
	
	private final Map<Path, Map<Track, Path>> extractedTrackMap = new HashMap<>();
	
	public ExtractedTracksProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Place a mapping into the proxy
	 * @param mediaFilePath the path to the MKV file
	 * @param track The track that was extracted
	 * @param extractedTrackFile The path to the extracted track file
	 */
	public synchronized void put(Path mediaFilePath, Track track, Path extractedTrackFile){
		Map<Track, Path> existingMappings = extractedTrackMap.get(mediaFilePath);
		
		if(existingMappings == null){
			existingMappings = new HashMap<Track, Path>();
			extractedTrackMap.put(mediaFilePath,  existingMappings);
		}
		
		existingMappings.put(track, extractedTrackFile);
	}
	
	/**
	 * Get the extracted tracks for a particular media file
	 * @param mediaFile The media file
	 * @return The extracted track map
	 */
	public synchronized Map<Track, Path> getExtractedTracks(Path mediaFile){
		Map<Track, Path> resultMap = new HashMap<Track, Path>();
		resultMap = extractedTrackMap.get(mediaFile);
		
		Map<Track, Path> finalResultMap = new HashMap<Track, Path>();
		for(Track t : resultMap.keySet()){
			Path result = resultMap.get(t);
			finalResultMap.put(t, result);
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
