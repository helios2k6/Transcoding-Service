package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;

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

	private volatile Thread runningThread;
	private volatile InterProcessPipe pipe;
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
		//Set current thread
		runningThread = Thread.currentThread();
		
		//Start decoder
		Optional<Process> decoder = startDecoder(job, decoderBuilder);
		if(decoder.isNone()){
			Log.error("Could not begin ffmpeg decoder process for: " + job.getRequest().getSourceFile());
			return;
		}

		//Start encoder
		Optional<Process> encoder = startEncoder(job, encoderBuilder);
		if(encoder.isNone()){
			Log.error("Could not begin x264 encoder process");
			ProcessUtils.tryCloseProcess(decoder);
			return;
		}

		//Get pipe
		Optional<InterProcessPipe> pipeOptional = startPipe(decoder.getValue(), encoder.getValue(), service);
		if(pipeOptional.isNone()){
			Log.error("Could not create interprocess pipe");
			ProcessUtils.tryCloseProcess(decoder);
			ProcessUtils.tryCloseProcess(encoder);
			return;
		}

		pipe = pipeOptional.getValue();
		
		while(isCancelled == false){
			pipe.pipe();
		}

		if(pipe.isFinished() && isCancelled == false){
			callback.completed(null, job);
		}else{
			callback.failed(null, job);
		}
	}

	/**
	 * Cancels the encoding runnable
	 */
	public void cancel(){
		//Set the cancelled flag
		isCancelled = true;
		
		//Cancel the pipe
		if(pipe != null){
			pipe.cancel();
		}
		
		//Interrupt the thread
		if(runningThread != null){
			runningThread.interrupt();
		}
	}

	private static Optional<InterProcessPipe> startPipe(Process decoder, Process encoder, ExecutorService service){
		return Optional.make(new InterProcessPipe(decoder, encoder, service));
	}

	private static Optional<Process> startEncoder(EncodingJob job, EncoderArgumentBuilder encoderBuilder){
		List<String> encodingOptions = encoderBuilder.getEncoderArguments(job, job.getDestinationFilePath());
		ProcessBuilder processBuilder = new ProcessBuilder(encodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}

	private static Optional<Process> startDecoder(EncodingJob job, DecoderArgumentBuilder decoderBuilder){
		List<String> decodingOptions = decoderBuilder.getDecoderArguments(job);
		ProcessBuilder processBuilder = new ProcessBuilder(decodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}
}
