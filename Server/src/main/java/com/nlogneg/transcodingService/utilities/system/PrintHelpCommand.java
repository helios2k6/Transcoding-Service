package com.nlogneg.transcodingService.utilities.system;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.configuration.CommandLineOptionsFactory;

/**
 * Prints the help to the screen
 * 
 * @author anjohnson
 * 
 */
public class PrintHelpCommand extends SimpleCommand
{
	@Override
	public void execute(final INotification notification)
	{
		final Options options = CommandLineOptionsFactory.createOptions();
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("<this program> [options]", options);
	}
}
