package com.nlogneg.transcodingService.mediaInfo;

public interface TrackVisitor{
	void visit(Track track);
	void visit(GeneralTrack track);
	void visit(MediaTrack track);
	void visit(AudioTrack track);
	void visit(TextTrack track);
	void visit(VideoTrack track);
}
