package com.nlogneg.transcodingService;

import org.puremvc.java.multicore.interfaces.IFacade;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.configuration.CommandLineOptionsProxy;
import com.nlogneg.transcodingService.configuration.ConfigurationFileProxy;
import com.nlogneg.transcodingService.configuration.ConfigureServerSocketCommand;
import com.nlogneg.transcodingService.configuration.ReadConfigurationFileCommand;
import com.nlogneg.transcodingService.demultiplex.DemultiplexController;
import com.nlogneg.transcodingService.demultiplex.DemultiplexJobStatusProxy;
import com.nlogneg.transcodingService.demultiplex.mkv.attachments.ScheduleAttachmentExtractionCommand;
import com.nlogneg.transcodingService.demultiplex.mkv.tracks.ScheduleDemultiplexMKVTrackCommand;
import com.nlogneg.transcodingService.encoding.EncodingController;
import com.nlogneg.transcodingService.encoding.EncodingJobStatusProxy;
import com.nlogneg.transcodingService.encoding.ScheduleAudioEncodingCommand;
import com.nlogneg.transcodingService.encoding.ScheduleVideoEncodeCommand;
import com.nlogneg.transcodingService.multiplex.mp4.ScheduleMultiplexCommand;
import com.nlogneg.transcodingService.request.incoming.RequestProxy;
import com.nlogneg.transcodingService.request.incoming.SerializedRequestProxy;
import com.nlogneg.transcodingService.request.server.ServerSocketProxy;
import com.nlogneg.transcodingService.request.server.ServerStatusProxy;
import com.nlogneg.transcodingService.request.server.SocketProxy;
import com.nlogneg.transcodingService.utilities.system.ExitSystemCommand;
import com.nlogneg.transcodingService.utilities.threads.ExecutorServiceProxy;

/**
 * The entry point of this program
 * 
 * @author anjohnson
 * 
 */
public final class Driver
{
	public static final String InstanceKey = "1";

	public static void main(final String[] args)
	{
		final IFacade facade = Facade.getInstance(InstanceKey);

		// Register proxies
		registerProxies(facade, args);

		// Register commands
		registerCommands(facade);

		// Read in config file
		readConfigFile(facade);

		// Launch setup commands
		setupServer(facade);

		// Begin listening on port
		beginServerLoop(facade);
	}

	private static void registerProxies(
			final IFacade facade,
			final String[] args)
	{
		facade.registerProxy(new CommandLineOptionsProxy());
		facade.registerProxy(new DemultiplexJobStatusProxy());
		facade.registerProxy(new EncodingJobStatusProxy());
		facade.registerProxy(new ExecutorServiceProxy());
		facade.registerProxy(new MediaFileRequestProxy());
		facade.registerProxy(new RequestProxy());
		facade.registerProxy(new SerializedRequestProxy());
		facade.registerProxy(new ConfigurationFileProxy());
		facade.registerProxy(new ServerSocketProxy());
		facade.registerProxy(new ServerStatusProxy());
		facade.registerProxy(new SocketProxy());
	}

	private static void registerCommands(final IFacade facade)
	{
		registerConfigurationLayer(facade);
		registerDemultiplexLayer(facade);
		registerEncodingLayer(facade);
		registerMultiplexLayer(facade);
		registerExitLayer(facade);
	}

	private static void registerExitLayer(final IFacade facade)
	{
		facade.registerCommand(
				ExitSystemCommand.ExitSystem,
				new ExitSystemCommand());
	}

	private static void registerConfigurationLayer(final IFacade facade)
	{
		facade.registerCommand(
				ReadConfigurationFileCommand.ReadConfigurationFile,
				new ReadConfigurationFileCommand());
		facade.registerCommand(
				ConfigureServerSocketCommand.ConfigureServerSocket,
				new ConfigureServerSocketCommand());
	}

	private static void registerDemultiplexLayer(final IFacade facade)
	{
		final DemultiplexController demultiplexController = new DemultiplexController();

		facade.registerCommand(
				DemultiplexController.StartDemultiplexJob,
				demultiplexController);
		facade.registerCommand(
				DemultiplexController.DemultiplexTrackSuccess,
				demultiplexController);
		facade.registerCommand(
				DemultiplexController.DemultiplexTrackFailure,
				demultiplexController);
		facade.registerCommand(
				DemultiplexController.DemultiplexAttachmentSuccess,
				demultiplexController);
		facade.registerCommand(
				DemultiplexController.DemultiplexAttachmentFailure,
				demultiplexController);

		final ScheduleAttachmentExtractionCommand scheduleAttachmentExtractionCommand = new ScheduleAttachmentExtractionCommand();
		facade.registerCommand(
				ScheduleAttachmentExtractionCommand.ScheduleAttachmentDemultiplexJob,
				scheduleAttachmentExtractionCommand);

		final ScheduleDemultiplexMKVTrackCommand scheduleDemultiplexMKVTrackCommand = new ScheduleDemultiplexMKVTrackCommand();
		facade.registerCommand(
				ScheduleDemultiplexMKVTrackCommand.ScheduleTrackDemultiplexJob,
				scheduleDemultiplexMKVTrackCommand);
	}

	private static void registerEncodingLayer(final IFacade facade)
	{
		final EncodingController controller = new EncodingController();

		facade.registerCommand(EncodingController.StartEncodingJob, controller);
		facade.registerCommand(
				EncodingController.EncodeVideoSuccess,
				controller);
		facade.registerCommand(
				EncodingController.EncodeVideoFailure,
				controller);
		facade.registerCommand(
				EncodingController.EncodeAudioSuccess,
				controller);
		facade.registerCommand(
				EncodingController.EncodeAudioFailure,
				controller);

		final ScheduleVideoEncodeCommand scheduleVideoEncodeCommand = new ScheduleVideoEncodeCommand();
		facade.registerCommand(
				ScheduleVideoEncodeCommand.ScheduleVideoEncode,
				scheduleVideoEncodeCommand);

		final ScheduleAudioEncodingCommand scheduleAudioEncodingCommand = new ScheduleAudioEncodingCommand();
		facade.registerCommand(
				ScheduleAudioEncodingCommand.ScheduleAudioEncode,
				scheduleAudioEncodingCommand);
	}

	private static void registerMultiplexLayer(final IFacade facade)
	{
		final ScheduleMultiplexCommand scheduleMultiplexCommand = new ScheduleMultiplexCommand();
		facade.registerCommand(
				ScheduleMultiplexCommand.ScheduleMultiplex,
				scheduleMultiplexCommand);
	}

	private static void readConfigFile(final IFacade facade)
	{
	}

	private static void setupServer(final IFacade facade)
	{
	}

	private static void beginServerLoop(final IFacade facade)
	{
	}
}
