package com.nlogneg.transcodingService.utilities.system;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
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
	
	private final class ProcessReader implements Runnable{
		private final int BufferSize = 1843200; //The expected size of one video frame @ 1280x720 4:2:0 24bits per pixel
		
		private final Process process;
		private final BlockingQueue<Byte> queue;
		
		
		public ProcessReader(Process process, BlockingQueue<Byte> queue){
			this.process = process;
			this.queue = queue;
		}
		
		@Override
		public void run() {
			InputStream standardOut = process.getInputStream();
			byte[] bufferArray = new byte[BufferSize];
			int bytesRead = 0;
			
			do{
				try{
					bytesRead = standardOut.read(bufferArray);
					
					for(byte b : bufferArray){
						queue.put(b);
					}
				}catch (IOException e){
					Log.error("Exception when reading from stream.", e);
					return;
				} catch (InterruptedException e) {
					Log.error("Interrupted thread.", e);
				}
			}while(bytesRead != -1);
		}
		
	}
	
	private static final AtomicLong IdGenerator = new AtomicLong();
	
	private final long id;
	private final Process source;
	private final Process sink;
	private final BlockingQueue<Byte> queue = new LinkedBlockingQueue<>();
	
	
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
