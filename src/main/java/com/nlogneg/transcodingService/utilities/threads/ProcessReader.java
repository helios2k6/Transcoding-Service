package com.nlogneg.transcodingService.utilities.threads;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Represents a Process reader that reads from an external process until
 * it dies or closes the pipe
 * @author anjohnson
 *
 */
public final class ProcessReader implements Runnable{
	private static final Logger Log = LogManager.getLogger(ProcessReader.class);
	private static final int BufferSize = 1843200;

	private final Process process;
	private final InputStream stream;
	private final ExecutorService service;
	private final byte[] buffer = new byte[BufferSize];
	private final BlockingQueue<byte[]> queue;
	
	private volatile boolean isCancelled = false;
	private volatile boolean isFinished = false;

	/**
	 * Creates a Process Reader
	 * @param process The process to monitor
	 * @param stream The stream to read from. Usually the Process' STDOUT
	 * @param service The executor service
	 * @param queue The queue to add bytes to
	 */
	public ProcessReader(
			Process process,
			InputStream stream,
			ExecutorService service, 
			BlockingQueue<byte[]> queue){
		this.process = process;
		this.stream = stream;
		this.service = service;
		this.queue = queue;
	}

	@Override
	public void run(){
		ProcessSignaler signaler = new ProcessSignaler(process);
		service.submit(signaler);

		while(isCancelled == false && isFinished == false){
			//Check to see if the external process is finished
			if(signaler.isProcessFinished()){
				isFinished = true;
			}else{
				try {
					if(stream.available() > 0){
						tryReadBytes();
					}else{
						Thread.sleep(50);
					}
				}catch (IOException e){
					isCancelled = true;
					Log.error("IO Error occurred while reading from process.", e);
				}catch (InterruptedException e){
					Log.error("This thread was interrupted.", e);
				}
			}
		}
		
		//Check cancellation status
		checkCancelStatus(signaler);
	}

	/**
	 * Returns whether or not this Process Reader finished reading from 
	 * the process successfully. A cancelled Process Reader is not considered
	 * finished
	 * @return
	 */
	public boolean isFinished(){
		return isFinished;
	}
	
	/**
	 * @return the isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * @param isCancelled the isCancelled to set
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	/**
	 * Cancels this Process Reader
	 */
	public void cancel(){
		isCancelled = true;
	}

	private void tryReadBytes() throws IOException{
		if(stream.available() > 0){
			int amountRead = stream.read(buffer);

			byte[] copy = new byte[amountRead];
			for(int i = 0; i < amountRead; i++){
				copy[i] = buffer[i];
			}

			queue.add(copy);
		}
	}

	private void checkCancelStatus(ProcessSignaler signaler){
		if(isCancelled){
			signaler.cancel();
		}
	}
}
