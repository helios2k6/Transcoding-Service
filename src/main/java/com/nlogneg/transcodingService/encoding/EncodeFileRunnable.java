package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;

/**
 * Encodes a file
 * @author Andrew
 *
 */
public class EncodeFileRunnable implements Runnable{

	private static final Logger Log = LogManager.getLogger(EncodeFileRunnable.class);
	
	private final EncodingJob job;
	private final CompletionHandler<Void, EncodingJob> callback;
	private final EncoderArgumentBuilder encoderArgumentBuilder;
	
	/**
	 * @param job
	 * @param callback
	 * @param encoderArgumentBuilder
	 */
	public EncodeFileRunnable(
			EncodingJob job,
			CompletionHandler<Void, EncodingJob> callback,
			EncoderArgumentBuilder encoderArgumentBuilder){
		this.job = job;
		this.callback = callback;
		this.encoderArgumentBuilder = encoderArgumentBuilder;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		Log.info("Encoding audio for file: " + job.getSourceFilePath());
		
		boolean result = encodeAudioTrack();
		if(result){
			callback.completed(null, job);
		}else{
			callback.failed(null, job);
		}
	}
	
	private boolean encodeAudioTrack(){
		Optional<Path> audioTrackPathOptional = job.getAudioTrackOption().getAudioTrackFilePath();
		
		if(audioTrackPathOptional.isNone()){
			Log.error("Could not encode audio track. No audio track path specified.");
			return false;
		}

		List<String> arguments = encoderArgumentBuilder.getEncoderArguments(job, job.getOutputAudioFile());
		Optional<Process> process = ProcessUtils.tryStartProcess(new ProcessBuilder(arguments));

		if(process.isNone()){
			Log.error("Could not start encoder process");
			return false;
		}
		
		return ProcessUtils.tryWaitForProcess(process.getValue());
	}
}
