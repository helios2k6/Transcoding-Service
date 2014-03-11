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

	private volatile Thread runningThread;
	private volatile boolean isCancelled;
	private volatile InterProcessPipe pipe;
	
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
		setThread();
		executeJob();
	}
	
	public void cancel(){
		lock.lock();
		isCancelled = true;
		
		if(runningThread != null){
			runningThread.interrupt();
		}
		lock.unlock();
	}
	
	private void setThread(){
		lock.lock();
		runningThread = Thread.currentThread();
		lock.unlock();
	}
	
	private void executeJob(){
		//HOOK UP RUNNABLE
	}
	
	private static Optional<InterProcessPipe> startPipe(Process decoder, Process encoder, ExecutorService service){
		return Optional.make(new InterProcessPipe(decoder, encoder, service));
	}
	
	private static Optional<Process> startEncoder(EncodingJob job, EncoderArgumentBuilder encoderBuilder, Path outputFile){
		List<String> encodingOptions = encoderBuilder.getEncoderArguments(job, outputFile);
		ProcessBuilder processBuilder = new ProcessBuilder(encodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}
	
	private static Optional<Process> startDecoder(EncodingJob job, DecoderArgumentBuilder decoderBuilder){
		List<String> decodingOptions = decoderBuilder.getDecoderArguments(job);
		ProcessBuilder processBuilder = new ProcessBuilder(decodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}
}
