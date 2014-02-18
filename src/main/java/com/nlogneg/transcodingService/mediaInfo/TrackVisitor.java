package com.nlogneg.transcodingService.mediaInfo;

/**
 * An object that can visit Track objects 
 * @author anjohnson
 *
 */
public interface TrackVisitor{
	void visit(Track track);
	void visit(MediaTrack track);
	void visit(GeneralTrack track);
	void visit(AudioTrack track);
	void visit(VideoTrack track);
}