package com.nlogneg.transcodingService.info.mediainfo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A track summary that summarizes all of the track information given a media
 * info object.
 * 
 * This object should only be used with one MediaInfo object because Tracks do
 * not have any context as to which MediaInfo they belong to
 * 
 * @author anjohnson
 * 
 */
public final class MediaInfoTrackSummary implements TrackVisitor
{

	private final Set<GeneralTrack> generalTracks = new HashSet<>();
	private final Set<VideoTrack> videoTracks = new HashSet<>();
	private final Set<AudioTrack> audioTracks = new HashSet<>();
	private final Set<TextTrack> subtitleTracks = new HashSet<>();

	/**
	 * @return the videoTracks
	 */
	public Collection<VideoTrack> getVideoTracks()
	{
		return this.videoTracks;
	}

	/**
	 * @return the audioTracks
	 */
	public Collection<AudioTrack> getAudioTracks()
	{
		return this.audioTracks;
	}

	/**
	 * @return the subtitleTracks
	 */
	public Collection<TextTrack> getSubtitleTracks()
	{
		return this.subtitleTracks;
	}

	public Collection<GeneralTrack> getGeneralTracks()
	{
		return this.generalTracks;
	}

	@Override
	public void visit(final Track track)
	{
		// do nothing
	}

	@Override
	public void visit(final GeneralTrack track)
	{
		this.generalTracks.add(track);
	}

	@Override
	public void visit(final MediaTrack track)
	{
		// do nothing
	}

	@Override
	public void visit(final AudioTrack track)
	{
		this.audioTracks.add(track);
	}

	@Override
	public void visit(final TextTrack track)
	{
		this.subtitleTracks.add(track);
	}

	@Override
	public void visit(final VideoTrack track)
	{
		this.videoTracks.add(track);
	}

}
