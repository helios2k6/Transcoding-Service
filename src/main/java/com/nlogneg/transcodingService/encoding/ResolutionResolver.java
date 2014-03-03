package com.nlogneg.transcodingService.encoding;

import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummary;
import com.nlogneg.transcodingService.info.mediainfo.MediaInfoTrackSummaryFactory;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.VideoTrack;
import com.nlogneg.transcodingService.info.mediainfo.VideoTrackUtils;
import com.nlogneg.transcodingService.request.incoming.Selector;
import com.nlogneg.transcodingService.utilities.math.IntegerUtils;
import com.nlogneg.transcodingService.utilities.media.WidthHeightTuple;

/**
 * Resolved the Selector resolution options to a single resolution
 * @author anjohnson
 *
 */
public final class ResolutionResolver{
	private static final Logger Log = LogManager.getLogger(ResolutionResolver.class);
	
	/**
	 * Resolve the resolution
	 * @param job
	 * @return
	 */
	public static WidthHeightTuple resolveResolution(EncodingJob job){
		Selector selector = job.getRequest().getSelector();
		WidthHeightTuple oldResolution = tryGetResolution(job);
		WidthHeightTuple resolvedResolution = oldResolution;
		
		if(oldResolution == null){
			Log.error("Could not get the original resolution of media file: " + job.getRequest().getSourceFile());
			return null;
		}
		
		if(selector.isForce169AspectRatio()){
			resolvedResolution = enforce169Resolution(oldResolution);
		}
		
		if(selector.isCapResolution()){
			WidthHeightTuple maxResolution = new WidthHeightTuple(selector.getMaxHeight(), selector.getMaxWidth());
			resolvedResolution = enforceMaxResolution(oldResolution, maxResolution);
		}
		
		return resolvedResolution;
	}
	
	private static WidthHeightTuple tryGetResolution(EncodingJob job){
		MediaInfoTrackSummary summary = MediaInfoTrackSummaryFactory.getSummary(job.getMediaInfo());
		Set<VideoTrack> videoTracks = summary.getVideoTracks();
		VideoTrack chosenVideoTrack = getEarliestVideoTrack(videoTracks);
		
		return VideoTrackUtils.tryParsePixelAmount(chosenVideoTrack);
	}
	
	private static <T extends MediaTrack> T getEarliestVideoTrack(Set<T> tracks){
		T lowestTrackId = null;
		for(T t : tracks){
			if(lowestTrackId == null){
				lowestTrackId = t;
				continue;
			}
			
			if(t.getId() < lowestTrackId.getId()){
				lowestTrackId = t;
			}
		}
		
		return lowestTrackId;
	}
	
	private static WidthHeightTuple enforce169Resolution(WidthHeightTuple oldResolution){
		int aspectCoefficient = IntegerUtils.gcd(oldResolution.getWidth(), oldResolution.getHeight());
		return new WidthHeightTuple(aspectCoefficient * 16, aspectCoefficient * 9);
	}
	
	private static WidthHeightTuple enforceMaxResolution(WidthHeightTuple oldResolution, WidthHeightTuple maxResolution){
		if(oldResolution.getWidth() <= maxResolution.getWidth() && oldResolution.getHeight() <= maxResolution.getHeight()){
			return oldResolution;
		}
		
		int aspectCoefficient = IntegerUtils.gcd(oldResolution.getWidth(), oldResolution.getHeight());
		int widthCoPrime = oldResolution.getWidth() / aspectCoefficient;
		int heightCoPrime = oldResolution.getHeight() / aspectCoefficient;
		
		int closestCoefficient = maxResolution.getWidth() / heightCoPrime;
		
		return new WidthHeightTuple(closestCoefficient * widthCoPrime, closestCoefficient * heightCoPrime);
		
	}
}
