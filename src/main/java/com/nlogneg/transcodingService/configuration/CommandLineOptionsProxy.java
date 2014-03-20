package com.nlogneg.transcodingService.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

import com.nlogneg.transcodingService.utilities.exceptions.InvalidOperationException;

/**
 * Holds all of the command line options that were passed to this application
 * 
 * @author anjohnson
 * 
 */
public class CommandLineOptionsProxy extends Proxy
{
	public static final String PROXY_NAME = "CommandLineOptionsProxy";
	private static final Logger Log = LogManager
			.getLogger(CommandLineOptionsProxy.class);

	private final CommandLine commandLine;
	private boolean legalParse = true;

	public CommandLineOptionsProxy(final String[] args)
	{
		super(PROXY_NAME);

		CommandLine temp = null;
		try
		{
			final CommandLineParser parser = new BasicParser();
			temp = parser
					.parse(CommandLineOptionsFactory.createOptions(), args);
		} catch (final ParseException ex)
		{
			Log.error("Could not parse command line options.", ex);
			this.legalParse = false;
			temp = null;
		} finally
		{
			this.commandLine = temp;
		}
	}

	/**
	 * Get whether or not the command line options passed are legal
	 */
	public boolean isCommandLineLegal()
	{
		return this.legalParse;
	}

	/**
	 * Gets whether or not to show the help message
	 */
	public boolean getHelpOption()
	{
		if (this.isCommandLineLegal() == false)
		{
			return true;
		}

		if (this.commandLine.hasOption(CommandLineOptionsFactory.HelpArgument))
		{
			return true;
		}

		return false;
	}

	/**
	 * Get configuration file
	 */
	public Path getConfigurationFile()
	{
		if (this.isCommandLineLegal() == false)
		{
			throw new InvalidOperationException(
					"Cannot get port number for illegal command line options");
		}

		final String result = this.commandLine
				.getOptionValue(CommandLineOptionsFactory.ConfigurationFileArgument);
		return Paths.get(result);
	}
}
