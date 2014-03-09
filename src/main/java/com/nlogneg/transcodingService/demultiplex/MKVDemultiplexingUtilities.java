package com.nlogneg.transcodingService.demultiplex;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.activation.MimeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.Track;
import com.nlogneg.transcodingService.info.mkv.Attachment;
import com.nlogneg.transcodingService.utilities.MimeTypeUtilities;

/**
 * Utility class for demultiplexing MKV Files
 * @author anjohnson
 *
 */
public final class MKVDemultiplexingUtilities{
	private static final Logger Log = LogManager.getLogger(MKVDemultiplexingUtilities.class);
	private static final MimeType FontMimeType = getFontMimeType();
	
	/**
	 * Filters the collection for attachments that are fonts
	 * @param attachments
	 * @return
	 */
	public static Collection<Attachment> getFontAttachments(Collection<Attachment> attachments){
		Set<Attachment> result = new HashSet<Attachment>();
		for(Attachment a : attachments){
			MimeType mime = a.getMimeType();
			if(MimeTypeUtilities.areEqual(mime, FontMimeType)){
				result.add(a);
			}
		}
		
		return result;
	}
	
	private static final MimeType getFontMimeType(){
		try{
			return new MimeType("application/x-truetype-font");
		}catch(Exception e){
			//Can't get here, because we know that this works
			Log.error("We made the impossible, possible.", e);
			return null;
		}
	}
	
	/**
	 * Attempts to deduce the "best" track to use given the media info.
	 * 
	 * The rules of selecting an appropriate track are as follows:
	 * - Japanese tracks take highest priority
	 * - The track with the smallest ID number is preferred
	 * 
	 * @param mediaTracks The media tracks to analyze
	 * @return which track to use or null if no suitable track could be found
	 */
	public static Track deduceMostLikelyTrack(Collection<MediaTrack> mediaTracks){
		MediaTrack selectedTrack = null;

		for(MediaTrack track : mediaTracks){
			if(selectedTrack == null){
				selectedTrack = track;
			}else{
				//See if the selected track is Japanese
				if(isTrackJapanese(selectedTrack)){
					/*
					 * If so, then the only way we'll unseat the previous
					 * selection is if the track ID is lower
					 */
					if(track.getId() < selectedTrack.getId()){
						selectedTrack = track;
					}
				}else{
					/*
					 * OK well now there's two ways to unseat the previous track:
					 * 
					 * 1. The candidate track is Japanese
					 * 2. The candidate track has a lower ID number 
					 */
					
					if(isTrackJapanese(track) || track.getId() < selectedTrack.getId()){
						selectedTrack = track;
					}
				}
			}
		}

		return selectedTrack;
	}
	
	private static boolean isTrackJapanese(MediaTrack track){
		JapaneseTrackVisitor visitor = new JapaneseTrackVisitor();
		track.accept(visitor);
		return visitor.getIsJapanese();
	}
}
