package com.nlogneg.transcodingService.utilities.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Monitors for the death of an external Process
 * @author anjohnson
 *
 */
public final class ProcessSignaler implements Runnable{
	private static final Logger Log = LogManager.getLogger(ProcessSignaler.class);

	private final Lock lock = new ReentrantLock();
	private volatile boolean isProcessFinished = false;
	private volatile boolean isCancelled = false;
	
	private final Process process;
	private Thread runningThread;

	public ProcessSignaler(Process process){
		this.process = process;
	}

	/**
	 * @return the isProcessFinished
	 */
	public boolean isProcessFinished(){
		return isProcessFinished;
	}

	/**
	 * Gets whether or not this process has been cancelled
	 * @return 
	 */
	public boolean isCancelled(){
		return isCancelled;
	}
	
	/**
	 * Cancels this Process Signaler
	 * @throws InterruptedException
	 */
	public void cancel(){
		lock.lock();
		
		isCancelled = true;
		if(runningThread != null){
			runningThread.interrupt();
		}
		
		lock.unlock();
	}

	@Override
	public void run(){
		setCurrentThread();
		
		while(isProcessFinished == false && isCancelled == false){
			try{
				process.waitFor();
				isProcessFinished = true;
			}catch (InterruptedException e){
				Log.error("Process Signaller interrupted", e);
			}
		}
	}

	private void setCurrentThread(){
		lock.lock();
		runningThread = Thread.currentThread();
		lock.unlock();
	}
}
