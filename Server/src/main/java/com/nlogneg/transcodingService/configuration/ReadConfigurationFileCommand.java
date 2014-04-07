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
 * 
 * @author anjohnson
 * 
 */
public class ReadConfigurationFileCommand extends SimpleCommand
{

	/**
	 * The command used to execute this command
	 */
	public static final String ReadConfigurationFile = "ReadConfigurationFile";

	private static final Logger Log = LogManager.getLogger(ReadConfigurationFileCommand.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.puremvc.java.multicore.patterns.command.SimpleCommand#execute(org
	 * .puremvc.java.multicore.interfaces.INotification)
	 */
	@Override
	public void execute(final INotification notification)
	{
		final CommandLineOptionsProxy commandLineOptionsProxy = (CommandLineOptionsProxy) this.getFacade().retrieveProxy(
				CommandLineOptionsProxy.PROXY_NAME);

		final String pathOfConfigurationFile = commandLineOptionsProxy.getConfigurationFile();
		final Path configurationFilePath = Paths.get(pathOfConfigurationFile);

		final Optional<String> configurationFileContents = readFile(configurationFilePath);

		if (configurationFileContents.isNone())
		{
			this.fail("Unable to read configuration file contents");
		}

		final Optional<ConfigurationFile> configFile = deserializeConfigurationFile(configurationFileContents.getValue());

		if (configFile.isNone())
		{
			this.fail("Unable to deserialize configuration file.");
		}

		final ConfigurationFileProxy configFileProxy = (ConfigurationFileProxy) this.getFacade().retrieveProxy(
				ConfigurationFileProxy.PROXY_NAME);
		configFileProxy.setConfigurationFile(configFile.getValue());
	}

	private void fail(final String message)
	{
		Log.error(message);
		this.sendNotification(ExitSystemCommand.ExitSystem, -1);
	}

	private static Optional<String> readFile(final Path path)
	{
		if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) == false)
		{
			return Optional.none();
		}

		try
		{
			final List<String> lines = Files.readAllLines(
					path,
					Charset.defaultCharset());
			return Optional.make(ListUtilities.flatten(lines));
		}
		catch (final IOException e)
		{
			Log.error("Could not read file contents", e);
		}
		return Optional.none();
	}

	private static Optional<ConfigurationFile> deserializeConfigurationFile(
			final String contents)
	{
		final XStream deserializer = SerializerFactory.getConfigurationFileSerializer();
		try
		{
			final ConfigurationFile file = (ConfigurationFile) deserializer.fromXML(contents);
			return Optional.make(file);
		}
		catch (final Exception e)
		{
			Log.error("Could not deserialize configuration file", e);
		}

		return Optional.none();
	}
}
