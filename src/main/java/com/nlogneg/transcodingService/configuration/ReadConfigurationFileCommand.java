package com.nlogneg.transcodingService.configuration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.utilities.ListUtilities;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.nlogneg.transcodingService.utilities.system.ExitSystemCommand;
import com.thoughtworks.xstream.XStream;

/**
 * Reads the configuration file
 * @author anjohnson
 *
 */
public class ReadConfigurationFileCommand extends SimpleCommand {
	
	/**
	 * The command used to execute this command
	 */
	public static final String ReadConfigurationFile = "ReadConfigurationFile";
	
	private static final Logger Log = LogManager.getLogger(ReadConfigurationFileCommand.class);

	/* (non-Javadoc)
	 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand#execute(org.puremvc.java.multicore.interfaces.INotification)
	 */
	@Override
	public void execute(INotification notification) {
		CommandLineOptionsProxy commandLineOptionsProxy = (CommandLineOptionsProxy)getFacade().retrieveProxy(CommandLineOptionsProxy.PROXY_NAME);
		
		String pathOfConfigurationFile = commandLineOptionsProxy.getConfigurationFile();
		Path configurationFilePath = Paths.get(pathOfConfigurationFile);

		Optional<String> configurationFileContents = readFile(configurationFilePath);
		
		if(configurationFileContents.isNone())
		{
			fail("Unable to read configuration file contents");
		}
		
		Optional<ConfigurationFile> configFile = deserializeConfigurationFile(configurationFileContents.getValue());
		
		if(configFile.isNone())
		{
			fail("Unable to deserialize configuration file.");
		}
		
		ConfigurationFileProxy configFileProxy = (ConfigurationFileProxy)getFacade().retrieveProxy(ConfigurationFileProxy.PROXY_NAME);
		configFileProxy.setConfigurationFile(configFile.getValue());
	}

	private void fail(String message)
	{
		Log.error(message);
		this.sendNotification(ExitSystemCommand.ExitSystem, -1);
	}
	
	private static Optional<String> readFile(Path path)
	{
		if(Files.exists(path, LinkOption.NOFOLLOW_LINKS) == false)
		{
			return Optional.none();
		}
		
		try {
			List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
			return Optional.make(ListUtilities.flatten(lines));
		} catch (IOException e) {
			Log.error("Could not read file contents", e);
		}
		return Optional.none();
	}

	private static Optional<ConfigurationFile> deserializeConfigurationFile(String contents)
	{
		XStream deserializer = SerializerFactory.getConfigurationFileSerializer();
		try
		{
			ConfigurationFile file = (ConfigurationFile)deserializer.fromXML(contents);
			return Optional.make(file);
		}
		catch(Exception e)
		{
			Log.error("Could not deserialize configuration file", e);
		}
		
		return Optional.none();
	}
}
