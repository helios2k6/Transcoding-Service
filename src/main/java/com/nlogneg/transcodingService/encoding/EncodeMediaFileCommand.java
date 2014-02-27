package com.nlogneg.transcodingService.encoding;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Encodes a media file
 * @author anjohnson
 *
 */
public abstract class EncodeMediaFileCommand extends SimpleCommand{
	private static final AtomicInteger counter = new AtomicInteger();
	
	private static final Logger Log = LogManager.getLogger(EncodeMediaFileCommand.class);
	
	public final void execute(INotification notification){
		EncodingJob job = (EncodingJob)notification.getBody();
		
		Log.info("Encoding file: " + job.getRequest().getSourceFile());
		
		executeJob(job);
	}
	
	private void executeJob(EncodingJob job){
		//Start decoder
		Optional<Process> decoder = startDecoder(job);
		if(decoder.isNone()){
			Log.error("Could not begin ffmpeg decoder process for: " + job.getRequest().getSourceFile().getPath());
			return;
		}
		
		//Start encoder
		String encodedOutputFile = "temp_encoded_file_" + counter.incrementAndGet() + ".264";
		Optional<Process> encoder = startEncoder(job, encodedOutputFile);
		if(encoder.isNone()){
			Log.error("Could not begin x264 encoder process");
			tryCloseProcess(decoder);
			return;
		}
	}
	
	private static void tryCloseProcess(Optional<Process> process){
		if(process.isSome()){
			Process p = process.getValue();
			p.destroy();
		}
	}
	
	private Optional<Process> startEncoder(EncodingJob job, String outputFile){
		List<String> encodingOptions = getAdditionalEncoderArguments(job);
		
		X264EncodingArgumentBuilder builder = new X264EncodingArgumentBuilder(outputFile, encodingOptions);
		
		List<String> arguments = builder.getArguments();
		
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		
		try{
			Process process = processBuilder.start();
			return Optional.make(process);
		}catch(IOException e){
			Log.error("Could not start x264 process.", e);
		}
		
		return Optional.none();
	}
	
	private Optional<Process> startDecoder(EncodingJob job){
		String sourceFilePath = job.getRequest().getSourceFile().getPath();
		
		List<String> decodingOptions = getAdditionalDecodingArguments(job);
		List<String> filterOptions = getAdditionalFilterOptions(job);
		List<String> outputOptions = getAdditionalDecoderOutputOptions(job);
		
		FFMPEGDecodingArgumentBuilder builder = 
				new FFMPEGDecodingArgumentBuilder(
						sourceFilePath,
						decodingOptions,
						filterOptions,
						outputOptions);
		
		List<String> arguments = builder.getArguments();
		
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		
		try{
			Process process = processBuilder.start();
			return Optional.make(process);
		}catch (IOException e){
			Log.error("Could not start ffmpeg.", e);
		}
		
		return Optional.none();
	}
	
	/**
	 * Get the decoder arguments for the FFMPEG Process 
	 * @param job
	 * @return
	 */
	protected abstract List<String> getAdditionalDecodingArguments(EncodingJob job);
	
	/**
	 * Get the FFMPEG filter options 
	 * @param job
	 * @return
	 */
	protected abstract List<String> getAdditionalFilterOptions(EncodingJob job);
	
	/**
	 * Get any decoder output options
	 * @param job
	 * @return
	 */
	protected abstract List<String> getAdditionalDecoderOutputOptions(EncodingJob job);
	
	/**
	 * Get the encoder arguments for the X264 Process
	 * @param job
	 * @return
	 */
	protected abstract List<String> getAdditionalEncoderArguments(EncodingJob job);
	
	/**
	 * Get the mutltiplexing arguments for the FFMpeg Process
	 * @param job
	 * @return
	 */
	protected abstract List<String> getMultiplexingArguments(EncodingJob job);
}
