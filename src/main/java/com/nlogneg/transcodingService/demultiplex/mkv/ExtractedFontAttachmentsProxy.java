package com.nlogneg.transcodingService.demultiplex.mkv;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Contains the paths of all of the font attachments
 * @author anjohnson
 *
 */
public class ExtractedFontAttachmentsProxy extends Proxy{
	public static final String PROXY_NAME = "ExtractedFontAttachmentsProxy";
	
	private final ConcurrentMap<String, Path> extractedFonts = new ConcurrentHashMap<String, Path>();
	
	public ExtractedFontAttachmentsProxy(){
		super(PROXY_NAME);
	}
	
	/**
	 * Add a font attachment entry
	 */
	public void add(Path path){
		extractedFonts.putIfAbsent(path.toAbsolutePath().toString(), path);
	}
	
	/**
	 * Add a collection of paths
	 * @param paths
	 */
	public void add(Collection<Path> paths){
		for(Path p : paths){
			add(p);
		}
	}
	
	/**
	 * Get all of the fonts that have been extracted so far
	 */
	public synchronized List<Path> getAllFonts(){
		List<Path> paths = new ArrayList<>();
		
		for(String key : extractedFonts.keySet()){
			Path p = extractedFonts.remove(key);
			paths.add(p);
		}
		
		return paths;
	}
}
