package com.nlogneg.transcodingService.multiplex;

import java.nio.channels.CompletionHandler;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;

public class MultiplexTracksRunnable implements Runnable{
	private static final Logger Log = LogManager.getLogger(MultiplexTracksRunnable.class);
	private final MultiplexJob job;
	private final MultiplexArgumentBuilder builder;
	private final CompletionHandler<Void, MultiplexJob> callback;

	/**
	 * @param job
	 * @param builder
	 * @param callback
	 */
	public MultiplexTracksRunnable(
			MultiplexJob job,
			MultiplexArgumentBuilder builder,
			CompletionHandler<Void, MultiplexJob> callback) {
		this.job = job;
		this.builder = builder;
		this.callback = callback;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		Log.info("Multiplexing files for job: " + job.getDestinationFile());

		List<String> args = builder.getMultiplexingArguments(job);
		ProcessBuilder builder = new ProcessBuilder(args);

		Optional<Process> process = ProcessUtils.tryStartProcess(builder);
		if(process.isNone()){
			fail();
			return;
		}

		boolean result = ProcessUtils.tryWaitForProcess(process.getValue());
		if(result){
			Log.info("Successfully multiplexed job for: " + job.getDestinationFile());
			callback.completed(null, job);
		}else{
			fail();
		}
	}

	private void fail(){
		Log.error("Could not start multiplexing job for: " + job.getDestinationFile());
		callback.failed(null, job);
	}
}
