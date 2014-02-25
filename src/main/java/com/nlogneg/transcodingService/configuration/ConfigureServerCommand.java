package com.nlogneg.transcodingService.configuration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.constants.Notifications;
import com.nlogneg.transcodingService.utilities.ListUtilities;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;

/**
 * Configures this server application 
 * @author anjohnson
 *
 */
public class ConfigureServerCommand extends SimpleCommand{

	private static final Logger Log = LogManager.getLogger(ConfigureServerCommand.class);

	public void execute(INotification notification){
		CommandLineOptionsProxy proxy = (CommandLineOptionsProxy)getFacade().retrieveProxy(CommandLineOptionsProxy.PROXY_NAME);
		Path configFile = proxy.getConfigurationFile();
		
		if(Files.exists(configFile, LinkOption.NOFOLLOW_LINKS) == false){
			fail("Config file cannot be found at: " + configFile.toAbsolutePath().toString(), null);
		}
		
		try{
			List<String> lines = Files.readAllLines(configFile, Charset.defaultCharset());
			String flattenedFile = ListUtilities.flatten(lines);
			
			XStream deserializer = SerializerFactory.getConfigurationFileDeserialization();
			ConfigurationFile file = (ConfigurationFile)deserializer.fromXML(flattenedFile);
			
			ServerConfigurationProxy serverConfigProxy = (ServerConfigurationProxy)getFacade().retrieveProxy(ServerConfigurationProxy.PROXY_NAME);
			serverConfigProxy.setConfigurationFile(file);
		}catch(IOException e){
			fail("Could not read config file.", e);
		}catch(Exception ex){
			fail("Could not deserialize configuration file.", ex);
		}
	}

	private void fail(String message, Exception ex){
		if(ex != null){
			Log.fatal(message, ex);
		}else{
			Log.fatal(message);
		}
		
		sendNotification(Notifications.PrintHelpAndExitMacroCommandNotification);
	}
}
