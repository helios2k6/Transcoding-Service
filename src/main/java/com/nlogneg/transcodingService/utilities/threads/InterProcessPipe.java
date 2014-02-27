package com.nlogneg.transcodingService.utilities.threads;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.BlockingQueue;
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
public final class InterProcessPipe implements Runnable, CompletionHandler<Void, Process>{
	private static final Logger Log = LogManager.getLogger(InterProcessPipe.class);
	
	private static final AtomicLong IdGenerator = new AtomicLong();
	
	private final long id;
	private final Process source;
	private final Process sink;
	private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
	
	/**
	 * @param source
	 * @param sink
	 */
	public InterProcessPipe(Process source, Process sink){
		this.source = source;
		this.sink = sink;
		this.id = IdGenerator.incrementAndGet();
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
	}

	@Override
	public void completed(Void arg0, Process arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void failed(Throwable arg0, Process arg1) {
		// TODO Auto-generated method stub
		
	}
}
