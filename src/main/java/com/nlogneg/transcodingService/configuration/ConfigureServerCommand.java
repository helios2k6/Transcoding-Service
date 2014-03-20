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
import com.thoughtworks.xstream.XStreamException;

/**
 * Configures this server application
 * 
 * @author anjohnson
 * 
 */
public class ConfigureServerCommand extends SimpleCommand
{

	private static final Logger Log = LogManager
			.getLogger(ConfigureServerCommand.class);

	@Override
	public void execute(final INotification notification)
	{
		final CommandLineOptionsProxy proxy = (CommandLineOptionsProxy) this
				.getFacade().retrieveProxy(CommandLineOptionsProxy.PROXY_NAME);
		final Path configFile = proxy.getConfigurationFile();

		if (Files.exists(configFile, LinkOption.NOFOLLOW_LINKS) == false)
		{
			this.fail("Config file cannot be found at: "
					+ configFile.toAbsolutePath().toString(), null);
		}

		try
		{
			final List<String> lines = Files.readAllLines(configFile,
					Charset.defaultCharset());
			final String flattenedFile = ListUtilities.flatten(lines);

			final XStream deserializer = SerializerFactory
					.getConfigurationFileSerializer();
			final ConfigurationFile file = (ConfigurationFile) deserializer
					.fromXML(flattenedFile);

			final ServerConfigurationProxy serverConfigProxy = (ServerConfigurationProxy) this
					.getFacade().retrieveProxy(
							ServerConfigurationProxy.PROXY_NAME);
			serverConfigProxy.setConfigurationFile(file);
		} catch (final IOException e)
		{
			this.fail("Could not read config file.", e);
		} catch (final XStreamException ex)
		{
			this.fail("Could not deserialize configuration file.", ex);
		}
	}

	private void fail(final String message, final Exception ex)
	{
		if (ex != null)
		{
			Log.fatal(message, ex);
		} else
		{
			Log.fatal(message);
		}

		this.sendNotification(Notifications.PrintHelpAndExit);
	}
}
