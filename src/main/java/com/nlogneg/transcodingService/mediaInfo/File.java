package com.nlogneg.transcodingService.mediaInfo;

import java.util.List;

/**
 * Represents the File section of a MediaInfo XML file
 * @author anjohnson
 *
 */
public class File{
	private List<Track> tracks;

	/**
	 * Get the tracks
	 * @return The tracks
	 */
	public List<Track> getTracks() {
		return tracks;
	}

	/**
	 * Set the tracks
	 * @param tracks The tracks
	 */
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	
}
