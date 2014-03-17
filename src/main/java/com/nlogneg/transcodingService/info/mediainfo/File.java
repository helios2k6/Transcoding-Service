package com.nlogneg.transcodingService.info.mediainfo;

import java.util.List;

/**
 * Represents the File section of a MediaInfo XML file
 * @author anjohnson
 *
 */
public class File{
	private final List<Track> tracks;

	/**
	 * Constructs a File object
	 * @param tracks The tracks of the File object
	 */
	public File(List<Track> tracks) {
		this.tracks = tracks;
	}

	/**
	 * Get the tracks
	 * @return The tracks
	 */
	public List<Track> getTracks(){
		return tracks;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tracks == null) ? 0 : tracks.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		File other = (File) obj;
		if (tracks == null){
			if (other.tracks != null){
				return false;
			}
		} else if (!tracks.equals(other.tracks)){
			return false;
		}
		return true;
	}
	
}