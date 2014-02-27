package com.nlogneg.transcodingService.utilities.threads;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Represents a pipe between two processes. This object must be run in a separate
 * thread so that it can promptly feed data between the source and sink
 * processes.
 * 
 * @author Andrew
 *
 */
public final class InterProcessPipe implements Runnable{
	private static final Logger Log = LogManager.getLogger(InterProcessPipe.class);
	
	private static final AtomicLong IdGenerator = new AtomicLong();
	
	private final long id;
	private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
	private final ProcessReader reader;
	private final ProcessWriter writer;
	private final ExecutorService service;
	private final CompletionHandler<Void, Void> completionHandler;
	
	private volatile boolean isCancelled = false;
	private volatile boolean isFinished = false;
	
	/**
	 * Constructs an InterProcessPipe line
	 * @param source The source process
	 * @param sink The sink process
	 * @param service The executor service
	 * @param completionHandler The callback
	 */
	public InterProcessPipe(Process source, Process sink, ExecutorService service, CompletionHandler<Void, Void> completionHandler){
		this.id = IdGenerator.incrementAndGet();
		this.reader = new ProcessReader(source, source.getInputStream(), service, queue);
		this.writer = new ProcessWriter(sink, sink.getOutputStream(), service, queue);
		this.service = service;
		this.completionHandler = completionHandler;
	}
	
	/**
	 * Get the id
	 * @return
	 */
	public long getId(){
		return id;
	}
	
	@Override
	public void run(){
		Log.info("Processing inter-process communication");
		
		service.submit(reader);
		service.submit(writer);
		
		while(isCancelled == false && isFinished == false){
			boolean readerFinished = reader.isFinished();
			boolean writerFinished = writer.isFinished();

			if(readerFinished && writerFinished){
				isFinished = true;
			}

			try{
				Thread.sleep(150);
			}catch (InterruptedException e){
				Log.error("Interprocess Pipe interrupted.", e);
			}
		}
		
		if(isCancelled){
			reader.cancel();
			writer.cancel();
			completionHandler.failed(null, null);
		}
		
		if(isFinished){
			completionHandler.completed(null, null);
		}
	}
	
	/**
	 * Gets whether this pipe is cancelled 
	 * @return
	 */
	public boolean isCancelled(){
		return isCancelled;
	}
	
	/**
	 * Cancels this pipe
	 */
	public void cancel(){
		isCancelled = true;
	}
}
