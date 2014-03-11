package com.nlogneg.transcodingService.demultiplex.mkv.tracks;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities;

/**
 * Demultiplexes a type of track
 * @author anjohnson
 *
 */
public final class DemultiplexTrackRunnable implements Runnable{
	private static final Logger Log = LogManager.getLogger(DemultiplexTrackRunnable.class);
	private static final String TracksArgument = "tracks";
	
	private final DemultiplexMKVJob job;
	private final CompletionHandler<Void, DemultiplexMKVJob> asyncCallback;
	
	/**
	 * @param job
	 * @param asyncCallback
	 */
	public DemultiplexTrackRunnable(
			DemultiplexMKVJob job,
			CompletionHandler<Void, DemultiplexMKVJob> asyncCallback){
		this.job = job;
		this.asyncCallback = asyncCallback;
	}

	@Override
	public final void run(){
		boolean audioTrackResult = extractAudioTracks();
		boolean subtitleTrackResult = extractSubtitleTracks();
		boolean finalResult = audioTrackResult && subtitleTrackResult;
		
		if(finalResult){
			asyncCallback.completed(null, job);
		}else{
			asyncCallback.failed(null, job);
		}
	}
	
	private boolean extractAudioTracks(){
		Log.info("Extracting audio tracks");
		return extractTracks(job.getAudioTrackMap());
	}
	
	private boolean extractSubtitleTracks(){
		Log.info("Extracting subtitle tracks.");
		return extractTracks(job.getSubtitleTrackMap());
	}
	
	private <T extends MediaTrack> boolean extractTracks(Map<T, Path> trackMap){
		Set<T> keyRing = trackMap.keySet();
		boolean extractionResult = true;
		
		for(T track : keyRing){
			Path outputPath = trackMap.get(track);
			boolean currentResult = extractTrack(track, outputPath);
			if(currentResult == false){
				extractionResult = false;
			}
		}
		
		return extractionResult;
	}
	
	private boolean extractTrack(MediaTrack track, Path outputFile){
		String mediaFileName = job.getMediaFile().toAbsolutePath().toString();
		StringBuilder argument = new StringBuilder();
		argument.append(track.getId()).append(":").append(outputFile.toAbsolutePath());
		
		ProcessBuilder builder = new ProcessBuilder(
				SystemUtilities.getMkvExtractProcessName(), 
				TracksArgument, 
				mediaFileName,
				argument.toString());
		
		Optional<Process> process = ProcessUtils.tryStartProcess(builder);
		
		if(process.isNone()){
			Log.error("Coudl not extract tracks for: " + job.getMediaFile());
			return false;
		}
		
		return ProcessUtils.tryWaitForProcess(process.getValue());
	}
}
