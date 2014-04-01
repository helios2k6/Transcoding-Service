package com.nlogneg.transcodingService;

import org.puremvc.java.multicore.interfaces.IFacade;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.configuration.CommandLineOptionsProxy;
import com.nlogneg.transcodingService.configuration.ServerConfigurationProxy;
import com.nlogneg.transcodingService.demultiplex.DemultiplexJobStatusProxy;
import com.nlogneg.transcodingService.encoding.EncodingJobStatusProxy;
import com.nlogneg.transcodingService.request.incoming.RequestProxy;
import com.nlogneg.transcodingService.request.incoming.SerializedRequestProxy;
import com.nlogneg.transcodingService.request.server.ServerSocketProxy;
import com.nlogneg.transcodingService.request.server.ServerStatusProxy;
import com.nlogneg.transcodingService.request.server.SocketProxy;
import com.nlogneg.transcodingService.utilities.threads.ExecutorProxy;

/**
 * The entry point of this program
 * @author anjohnson
 *
 */
public final class Driver 
{
	public static final String InstanceKey = "1";
	
	public static void main(String[] args)
	{
		IFacade facade = Facade.getInstance(InstanceKey);
		
		//Register proxies
		registerProxies(facade, args);
		
		//Register commands
		registerCommands(facade);
		
		//Read in config file
		readConfigFile(facade);
		
		//Launch setup commands
		setupServer(facade);
		
		//Begin listening on port
		beginServerLoop(facade);
	}
	
	private static void registerProxies(IFacade facade, String[] args)
	{
		facade.registerProxy(new CommandLineOptionsProxy(args));
		facade.registerProxy(new DemultiplexJobStatusProxy());
		facade.registerProxy(new EncodingJobStatusProxy());
		facade.registerProxy(new ExecutorProxy());
		facade.registerProxy(new MediaFileRequestProxy());
		facade.registerProxy(new RequestProxy());
		facade.registerProxy(new SerializedRequestProxy());
		facade.registerProxy(new ServerConfigurationProxy());
		facade.registerProxy(new ServerSocketProxy());
		facade.registerProxy(new ServerStatusProxy());
		facade.registerProxy(new SocketProxy());
	}
	
	private static void registerCommands(IFacade facade)
	{
		
	}
	
	private static void readConfigFile(IFacade facade)
	{
	}
	
	private static void setupServer(IFacade facade)
	{
	}
	
	private static void beginServerLoop(IFacade facade)
	{
	}
}
