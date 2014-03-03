package com.nlogneg.transcodingService.encoding.ffmpeg;

import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.encoding.EncodingAction;
import com.nlogneg.transcodingService.encoding.EncodingJob;
import com.nlogneg.transcodingService.encoding.SubtitleTrackOption;
import com.nlogneg.transcodingService.encoding.WidthHeightTuple;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Represents an argument builder that corresponds to the FFMPEG filter
 * system
 * @author anjohnson
 *
 */
public final class FFMPEGFilterArgumentBuilder{
	private static final Logger Log = LogManager.getLogger(FFMPEGFilterArgumentBuilder.class);
	
	private final Optional<WidthHeightTuple> resizeResolution;
	private final SubtitleTrackOption subtitleOverlay;
	/**
	 * @param resizeResolution
	 * @param subtitleOverlay
	 */
	public FFMPEGFilterArgumentBuilder(
			Optional<WidthHeightTuple> resizeResolution,
			SubtitleTrackOption subtitleOverlay){
		this.resizeResolution = resizeResolution;
		this.subtitleOverlay = subtitleOverlay;
	}
	
	public void addVideoFilterArguments(List<String> arguments, EncodingJob job){
		if(shouldResize() == false && shouldOverlaySubtitles() == false){
			return;
		}
		
		arguments.add("-vf");
		
		StringBuilder finalArgumentBuilder = new StringBuilder();
		
		String subtitleArgumentPart = getSubtitleOverlayArgument(job);
		if(subtitleArgumentPart != null){
			finalArgumentBuilder.append(subtitleArgumentPart);
		}
		
		String resizeArgument = getResizeArgument();
		if(resizeArgument != null){
			finalArgumentBuilder.append(",").append(resizeArgument);
		}
		
		arguments.add(finalArgumentBuilder.toString());
	}
	
	private boolean shouldResize(){
		return resizeResolution.isSome();
	}
	
	private boolean shouldOverlaySubtitles(){
		return subtitleOverlay.getEncodingActions().contains(EncodingAction.Encode);
	}
	
	private String getSubtitleOverlayArgument(EncodingJob job){
		SubtitleTrackOption subtitleOption = job.getSubtitleTrackOption();
		
		if(subtitleOption.getEncodingActions().contains(EncodingAction.Encode)){
			Optional<Path> extractedOptionalSubtitleFile = subtitleOption.getTextTrackFilePath();
			if(extractedOptionalSubtitleFile.isSome()){
				Path extractedSubtitleFile = extractedOptionalSubtitleFile.getValue();
				
				StringBuilder argBuilder = new StringBuilder();
				argBuilder.append("\"").append("ass=").append(extractedSubtitleFile.toAbsolutePath().toString()).append("\"");
				
				return argBuilder.toString();
			}else{
				Log.error("Subtitle track option was ENCODE but no extracted subtitle track could be found");
			}
		}
		
		return null;
	}
	
	private String getResizeArgument(){
		if(resizeResolution.isSome()){
			StringBuilder argumentBuilder = new StringBuilder();
			argumentBuilder.append("scale=").append(resizeResolution.getValue().getWidth());
			argumentBuilder.append(":").append(resizeResolution.getValue().getHeight()).append(";");
			return argumentBuilder.toString();
		}
		
		return null;
	}
}
