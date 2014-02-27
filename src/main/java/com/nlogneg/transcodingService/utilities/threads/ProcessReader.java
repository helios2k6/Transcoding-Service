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

		while(signaler.isProcessFinished() == false 
				&& signaler.isCancelled() == false
				&& isCancelled == false){
			try{
				if(stream.available() > 0){
					readBytes();
				}else{
					//Wait for a while
					Thread.sleep(500);
				}
				checkInterruptedStatus(signaler);
			}catch (IOException e){
				Log.error("Could not read from stream.", e);
				return;
			}catch (InterruptedException e){
				Log.error("Interrupted Process Reader.", e);
			}
		}
	}

	public void cancel(){
		isCancelled = true;
	}
	
	private void readBytes() throws IOException, InterruptedException {
		int amountRead = stream.read(buffer);
		
		byte[] copy = new byte[amountRead];
		for(int i = 0; i < amountRead; i++){
			copy[i] = buffer[i];
		}
		
		queue.add(copy);
	}

	private void checkInterruptedStatus(ProcessSignaler signaler) throws InterruptedException {
		if(Thread.interrupted()){
			signaler.cancel();
			Thread.currentThread().interrupt();
			throw new InterruptedException("Interrupted Process Signaller while sleeping.");
		}
	}
}
