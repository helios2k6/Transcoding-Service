package com.nlogneg.transcodingService.encoding;

import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;
import com.nlogneg.transcodingService.utilities.threads.InterProcessPipe;

/**
 * Encodes a media file
 * @author anjohnson
 *
 */
public final class EncodeVideoCommand extends SimpleCommand implements CompletionHandler<Void, EncodingJob>{
	private static final AtomicInteger Counter = new AtomicInteger();
	private static final Logger Log = LogManager.getLogger(EncodeVideoCommand.class);
	
	private final DecoderArgumentBuilder decoderBuilder;
	private final EncoderArgumentBuilder encoderBuilder;
	
	/**
	 * @param decoderBuilder
	 * @param encoderBuilder
	 */
	public EncodeVideoCommand(
			DecoderArgumentBuilder decoderBuilder,
			EncoderArgumentBuilder encoderBuilder){
		this.decoderBuilder = decoderBuilder;
		this.encoderBuilder = encoderBuilder;
	}

	public final void execute(INotification notification){
		EncodingJob job = (EncodingJob)notification.getBody();
		
		Log.info("Encoding file: " + job.getRequest().getSourceFile());
		
		executeJob(job);
	}
	
	private void executeJob(EncodingJob job){
		//Start decoder
		Optional<Process> decoder = startDecoder(job);
		if(decoder.isNone()){
			Log.error("Could not begin ffmpeg decoder process for: " + job.getRequest().getSourceFile());
			return;
		}
		
		//Start encoder
		Path encodedOutputFile = Paths.get("temp_encoded_file_" + Counter.incrementAndGet() + ".264");
		Optional<Process> encoder = startEncoder(job, encodedOutputFile);
		if(encoder.isNone()){
			Log.error("Could not begin x264 encoder process");
			ProcessUtils.tryCloseProcess(decoder);
			return;
		}
		
		//Hook up pipe between the two
		ExecutorProxy executorProxy = (ExecutorProxy)getFacade().retrieveProxy(ExecutorProxy.PROXY_NAME);
		ExecutorService service = executorProxy.getService();
		
		InterProcessPipe<EncodingJob> pipe = new InterProcessPipe<EncodingJob>(
				decoder.getValue(), 
				encoder.getValue(),
				service,
				job, 
				this);
		
		//Submit to executor
		service.submit(pipe);
	}
	
	private Optional<Process> startEncoder(EncodingJob job, Path outputFile){
		List<String> encodingOptions = encoderBuilder.getEncoderArguments(job, outputFile);
		ProcessBuilder processBuilder = new ProcessBuilder(encodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}
	
	private Optional<Process> startDecoder(EncodingJob job){
		List<String> decodingOptions = decoderBuilder.getDecoderArguments(job);
		ProcessBuilder processBuilder = new ProcessBuilder(decodingOptions);
		return ProcessUtils.tryStartProcess(processBuilder);
	}
	
	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#completed(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void completed(Void arg0, EncodingJob job){
		sendNotification(Notifications.EncodeVideoCommandSuccessNotification, job);
	}

	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable, java.lang.Object)
	 */
	@Override
	public void failed(Throwable arg0, EncodingJob job){
		sendNotification(Notifications.EncodeVideoCommandFailureNotification, job);;
	}
}
