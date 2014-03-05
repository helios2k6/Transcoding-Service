package com.nlogneg.transcodingService.utilities.system;

import java.nio.channels.CompletionHandler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Represents an asynchronous external Process that will notify when the process
 * completes
 * @author anjohnson
 *
 */
public final class AsyncProcessSignaler<T> implements Runnable{
	private static final Logger Log = LogManager.getLogger(AsyncProcessSignaler.class);
	
	private final Process process;
	private final CompletionHandler<Void, T> completionHandler;
	private final T token;
	private volatile Thread currentThread;
	private volatile boolean isCancelled = false;
	private volatile boolean didFinishSuccessfully = false;
	
	public AsyncProcessSignaler(Process process, CompletionHandler<Void, T> completionHandler, T token){
		this.process = process;
		this.completionHandler = completionHandler;
		this.token = token;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		currentThread = Thread.currentThread();
		while(didFinishSuccessfully == false && isCancelled == false){
			try {
				process.waitFor();
				didFinishSuccessfully = true;
			}catch (InterruptedException e){
				Log.error("Interrupted async process signaler.", e);
			}
		}
		
		if(didFinishSuccessfully){
			completionHandler.completed(null, token);
		}else{
			completionHandler.failed(null, token);
		}
	}

	/**
	 * Cancels the current Async Process
	 */
	public void cancel(){
		isCancelled = true;
		currentThread.interrupt();
	}

	/**
	 * @return the isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * @return Whether this async process finished successfully
	 */
	public boolean finishedSuccessfully() {
		return didFinishSuccessfully;
	}
}
