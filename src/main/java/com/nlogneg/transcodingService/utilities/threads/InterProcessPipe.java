package com.nlogneg.transcodingService.utilities.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

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
public final class InterProcessPipe{
	private static final Logger Log = LogManager.getLogger(InterProcessPipe.class);
	
	private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
	private final ProcessReader reader;
	private final ProcessWriter writer;
	private final ExecutorService service;
	
	private volatile boolean isCancelled = false;
	private volatile boolean isFinished = false;
	
	/**
	 * Constructs an InterProcessPipe line
	 * @param source The source process
	 * @param sink The sink process
	 * @param service The executor service
	 * 
	 */
	public InterProcessPipe(Process source, Process sink, ExecutorService service){
		this.reader = new ProcessReader(source, source.getInputStream(), service, queue);
		this.writer = new ProcessWriter(sink, sink.getOutputStream(), service, queue);
		this.service = service;
	}
	
	/**
	 * Pipes data between two processes
	 */
	public void pipe(){
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
	
	/**
	 * Gets whether this pipe finished
	 * @return
	 */
	public boolean isFinished(){
		return isFinished;
	}
}
