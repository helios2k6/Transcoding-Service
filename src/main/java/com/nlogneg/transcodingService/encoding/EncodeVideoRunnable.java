package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;
import com.nlogneg.transcodingService.utilities.threads.InterProcessPipe;

public final class EncodeVideoRunnable implements Runnable{

	private static final Logger Log = LogManager.getLogger(EncodeVideoRunnable.class);
	
	private final EncodingJob job;
	private final DecoderArgumentBuilder decoderBuilder;
	private final EncoderArgumentBuilder encoderBuilder;
	private final CompletionHandler<Void, EncodingJob> callback;
	private final ExecutorService service;
	private final Lock lock = new ReentrantLock();
	//TODO MAKE CANCELLABLE
	private volatile Thread runningThread;
	private volatile boolean isCancelled;
	
	/**
	 * @param job
	 * @param decoderBuilder
	 * @param encoderBuilder
	 * @param callback
	 * @param service
	 */
	public EncodeVideoRunnable(EncodingJob job,
			DecoderArgumentBuilder decoderBuilder,
			EncoderArgumentBuilder encoderBuilder,
			CompletionHandler<Void, EncodingJob> callback,
			ExecutorService service) {
		this.job = job;
		this.decoderBuilder = decoderBuilder;
		this.encoderBuilder = encoderBuilder;
		this.callback = callback;
		this.service = service;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		executeJob();
	}
	
	private void executeJob(){
		//Start decoder
		Optional<Process> decoder = startDecoder(job);
		if(decoder.isNone()){
			Log.error("Could not begin ffmpeg decoder process for: " + job.getRequest().getSourceFile());
			return;
		}
		
		//Start encoder
		Optional<Process> encoder = startEncoder(job, job.getDestinationFilePath());
		if(encoder.isNone()){
			Log.error("Could not begin x264 encoder process");
			ProcessUtils.tryCloseProcess(decoder);
			return;
		}
		
		//Hook up pipe between the two
		InterProcessPipe pipe = new InterProcessPipe(decoder.getValue(), encoder.getValue(), service);
		pipe.pipe();
	}
	
	private Optional<Process> startEncoder(EncodingJob job, Path outputFile){
		List<String> encodingOptions = encoderBuilder.getEncoderArguments(job, outputFile);
		ProcessBuilder processBuilder = new ProcessBuilder(encodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}
	
	private Optional<Process> startDecoder(EncodingJob job){
		List<String> decodingOptions = decoderBuilder.getDecoderArguments(job);
		ProcessBuilder processBuilder = new ProcessBuilder(decodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}
}
