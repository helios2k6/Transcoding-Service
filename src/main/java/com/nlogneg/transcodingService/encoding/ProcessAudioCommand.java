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
import com.nlogneg.transcodingService.encoding.neroAac.NeroAacArgumentBuilder;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.system.AsyncProcessSignaler;
import com.nlogneg.transcodingService.utilities.system.ProcessUtils;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * Processes the audio track of a given EncodingJob
 * @author anjohnson
 *
 */
public class ProcessAudioCommand extends SimpleCommand implements CompletionHandler<Void, EncodingJob>{
	private static final AtomicInteger IdGenerator = new AtomicInteger();
	
	private static final Logger Log = LogManager.getLogger(ProcessAudioCommand.class);
	public void execute(INotification notification){
		EncodingJob job = (EncodingJob)notification.getBody();
		AudioTrackOption audioOption = job.getAudioTrackOption();
		
		//Check to see what we do with the audio track
		if(audioOption.getEncodingAction() != EncodingAction.Encode){
			return;
		}
		
		Log.info("Encoding audio track");
		encodeAudioTrack(job);
	}
	
	private void encodeAudioTrack(EncodingJob job){
		Optional<Path> audioTrackPathOptional = job.getAudioTrackOption().getAudioTrackFilePath();
		if(audioTrackPathOptional.isNone()){
			Log.error("Could not encode audio track. No audio track path specified.");
			return;
		}
		
		Path outputFilePath = generateOutputFilePath(audioTrackPathOptional.getValue());
		EncoderArgumentBuilder builder = new NeroAacArgumentBuilder();
		List<String> arguments = builder.getEncoderArguments(job, outputFilePath);
		Optional<Process> process = ProcessUtils.tryStartProcess(new ProcessBuilder(arguments));
		
		if(process.isNone()){
			Log.error("Could not start neroAacEnc process");;
			return;
		}
		
		EncodedAudioFileProxy proxy = (EncodedAudioFileProxy)getFacade().retrieveProxy(EncodedAudioFileProxy.PROXY_NAME);
		proxy.put(job, outputFilePath);
		
		ExecutorProxy executorProxy = (ExecutorProxy)getFacade().retrieveProxy(ExecutorProxy.PROXY_NAME);
		ExecutorService service = executorProxy.getService();
		AsyncProcessSignaler<EncodingJob> signaler = new AsyncProcessSignaler<>(process.getValue(), this, job);
		
		//Submit the job so that it'll asynchronously complete
		service.submit(signaler);
	}
	
	private static Path generateOutputFilePath(Path audioTrackFile){
		String audioTrackFileName = audioTrackFile.getFileName().toString();
		StringBuilder builder = new StringBuilder();
		
		builder.append(audioTrackFileName);
		builder.append("_");
		builder.append(IdGenerator.incrementAndGet());
		builder.append(".m4a");
		
		return Paths.get(builder.toString());
	}

	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#completed(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void completed(Void arg0, EncodingJob arg1){
		sendNotification(Notifications.EncodeAudioCommandSuccessNotification, arg1);
	}

	/* (non-Javadoc)
	 * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable, java.lang.Object)
	 */
	@Override
	public void failed(Throwable arg0, EncodingJob arg1){
		sendNotification(Notifications.EncodeAudioCommandFailureNotification, arg1);
	}
}
