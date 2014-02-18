package com.nlogneg.transcodingService.serialization;

import com.nlogneg.transcodingService.mediaInfo.AudioTrack;
import com.nlogneg.transcodingService.mediaInfo.GeneralTrack;
import com.nlogneg.transcodingService.mediaInfo.MediaTrack;
import com.nlogneg.transcodingService.mediaInfo.Track;
import com.nlogneg.transcodingService.mediaInfo.TrackVisitor;
import com.nlogneg.transcodingService.mediaInfo.VideoTrack;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TrackSerializerVisitor implements TrackVisitor{
	private final HierarchicalStreamWriter streamWriter;
	
	public TrackSerializerVisitor(HierarchicalStreamWriter streamWriter){
		this.streamWriter = streamWriter;
	}

	@Override
	public void visit(Track track) {
	}

	@Override
	public void visit(MediaTrack track) {
	}

	@Override
	public void visit(GeneralTrack track) {
	}

	@Override
	public void visit(AudioTrack track) {
	}

	@Override
	public void visit(VideoTrack track) {
	}
	
	private static void serializeBaseTrack(Track track, HierarchicalStreamWriter writer){
		
	}
	
	private static void serializeMediaTrack(MediaTrack mediaTrack, HierarchicalStreamWriter writer){
		
	}
	
}
