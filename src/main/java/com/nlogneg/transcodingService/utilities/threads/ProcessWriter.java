package com.nlogneg.transcodingService.utilities.threads;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public final class ProcessWriter implements Runnable{
	private static final Logger Log = LogManager.getLogger(ProcessWriter.class);
	
	private final Process process;
	private final OutputStream stream;
	private final BlockingQueue<byte[]> queue;
	private final ExecutorService service;
	
	private volatile boolean isCancelled = false;
	
	/**
	 * @param process
	 * @param stream
	 * @param queue
	 * @param service
	 * @param isCancelled
	 */
	public ProcessWriter(Process process, OutputStream stream,
			BlockingQueue<byte[]> queue, ExecutorService service,
			boolean isCancelled) {
		this.process = process;
		this.stream = stream;
		this.queue = queue;
		this.service = service;
		this.isCancelled = isCancelled;
	}

	@Override
	public void run(){
		ProcessSignaler signaler = new ProcessSignaler(process);
		service.submit(signaler);
		
		while(signaler.isProcessFinished() == false
				&& signaler.isCancelled() == false
				&& isCancelled == false){
			
			try{
				byte[] chunk = queue.poll();
				if(chunk == null){
					Thread.sleep(500);
				}else{
					stream.write(chunk);
				}
			}catch(InterruptedException e){
				Log.error("Process Writer interrupted.", e);
			} catch (IOException e) {
				Log.error("Process Writer could not write to output stream.", e);
				return;
			}
		}
	}

}
