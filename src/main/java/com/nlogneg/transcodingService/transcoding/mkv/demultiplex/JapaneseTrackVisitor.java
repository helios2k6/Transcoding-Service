package com.nlogneg.transcodingService.transcoding.mkv.demultiplex;

import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.GeneralTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.info.mediainfo.Track;
import com.nlogneg.transcodingService.info.mediainfo.TrackVisitor;
import com.nlogneg.transcodingService.info.mediainfo.VideoTrack;

public class JapaneseTrackVisitor implements TrackVisitor{
	private boolean isJapanese = false;
	
	/**
	 * Returns whether or not the track visited is Japanese
	 * @return
	 */
	public boolean getIsJapanese(){
		return isJapanese;
	}
	
	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.mediaInfo.TrackVisitor#visit(com.nlogneg.transcodingService.mediaInfo.Track)
	 */
	@Override
	public void visit(Track track){
	}

	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.mediaInfo.TrackVisitor#visit(com.nlogneg.transcodingService.mediaInfo.GeneralTrack)
	 */
	@Override
	public void visit(GeneralTrack track){
	}

	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.mediaInfo.TrackVisitor#visit(com.nlogneg.transcodingService.mediaInfo.MediaTrack)
	 */
	@Override
	public void visit(MediaTrack track){
	}

	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.mediaInfo.TrackVisitor#visit(com.nlogneg.transcodingService.mediaInfo.AudioTrack)
	 */
	@Override
	public void visit(AudioTrack track){
		isJapanese = isJapanese(track.getLanguage());
	}

	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.mediaInfo.TrackVisitor#visit(com.nlogneg.transcodingService.mediaInfo.TextTrack)
	 */
	@Override
	public void visit(TextTrack track){
		isJapanese = isJapanese(track.getLanguage());
	}

	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.mediaInfo.TrackVisitor#visit(com.nlogneg.transcodingService.mediaInfo.VideoTrack)
	 */
	@Override
	public void visit(VideoTrack track){
	}
	
	private static boolean isJapanese(String language){
		if(language != null){
			return language.equalsIgnoreCase("Japanese");
		}
		
		return false;
	}
}
