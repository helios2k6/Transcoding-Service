package com.nlogneg.transcodingService.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public final class ConfigureLoggingCommand extends SimpleCommand
{
	private static final Logger Log = LogManager.getLogger(ConfigureLoggingCommand.class);

	@Override
	public void execute(final INotification notification)
	{
		final String logConfiguration = (String) notification.getBody();

		Log.info("Configuring logging");
		DOMConfigurator.configure(logConfiguration);
		Log.info("Finished configurating logging.");
	}
}
