package com.nlogneg.transcodingService.info.mediainfo;

import java.util.HashSet;
import java.util.Set;

/**
 * A track summary that summarizes all of the track information given a 
 * media info object.
 * 
 * This object should only be used with one MediaInfo object because Tracks
 * do not have any context as to which MediaInfo they belong to
 * @author anjohnson
 *
 */
public class MediaInfoTrackSummary implements TrackVisitor{

	private final Set<VideoTrack> videoTracks = new HashSet<>();
	private final Set<AudioTrack> audioTracks = new HashSet<>();
	private final Set<TextTrack> subtitleTracks = new HashSet<>();
	
	/**
	 * @return the videoTracks
	 */
	public Set<VideoTrack> getVideoTracks(){
		return videoTracks;
	}

	/**
	 * @return the audioTracks
	 */
	public Set<AudioTrack> getAudioTracks(){
		return audioTracks;
	}

	/**
	 * @return the subtitleTracks
	 */
	public Set<TextTrack> getSubtitleTracks(){
		return subtitleTracks;
	}

	@Override
	public void visit(Track track){
		//do nothing
	}

	@Override
	public void visit(GeneralTrack track){
		//do nothing
	}

	@Override
	public void visit(MediaTrack track){
		//do nothing
	}

	@Override
	public void visit(AudioTrack track){
		audioTracks.add(track);
	}

	@Override
	public void visit(TextTrack track){
		subtitleTracks.add(track);
	}

	@Override
	public void visit(VideoTrack track){
		videoTracks.add(track);
	}

}
