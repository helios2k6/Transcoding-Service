package com.nlogneg.transcodingService.demultiplex.mkv.tracks;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.demultiplex.mkv.DemultiplexMKVJob;
import com.nlogneg.transcodingService.info.mediainfo.AudioTrack;
import com.nlogneg.transcodingService.info.mediainfo.MediaTrack;
import com.nlogneg.transcodingService.info.mediainfo.TextTrack;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.Tuple;
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
		boolean audioTrackResult = extractAudioTrack();
		boolean subtitleTrackResult = extractSubtitleTrack();
		boolean finalResult = audioTrackResult && subtitleTrackResult;
		
		if(finalResult){
			asyncCallback.completed(null, job);
		}else{
			asyncCallback.failed(null, job);
		}
	}
	
	private boolean extractAudioTrack(){
		Log.info("Extracting audio tracks");
		Optional<Tuple<AudioTrack, Path>> audioTrackTupleOptional = job.getAudioTrack();
		
		if(audioTrackTupleOptional.isNone()){
			return true;
		}
		
		Tuple<AudioTrack, Path> audioTrackTuple = audioTrackTupleOptional.getValue();
		return extractTrack(audioTrackTuple.item1(), audioTrackTuple.item2());
	}
	
	private boolean extractSubtitleTrack(){
		Log.info("Extracting subtitle tracks.");
		Optional<Tuple<TextTrack, Path>> subtitleTrackTupleOptional = job.getSubtitleTrack();
		
		if(subtitleTrackTupleOptional.isNone()){
			return true;
		}
		
		Tuple<TextTrack, Path> subtitleTrackTuple = subtitleTrackTupleOptional.getValue();
		return extractTrack(subtitleTrackTuple.item1(), subtitleTrackTuple.item2());
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
			Log.error("Could not extract tracks for: " + job.getMediaFile());
			return false;
		}
		
		return ProcessUtils.tryWaitForProcess(process.getValue());
	}
}
