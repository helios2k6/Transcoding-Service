package com.nlogneg.transcodingService.configuration;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Constructs command line options used to parse the command line arguments
 * 
 * @author anjohnson
 * 
 */
public class CommandLineOptionsFactory
{
	public static final String HelpArgument = "help";
	public static final String ConfigurationFileArgument = "configuration-file";

	/**
	 * Construct standard command line options for argument parsing
	 * 
	 * @return A new set of options
	 */
	public static Options createOptions()
	{
		final Options options = new Options();
		options.addOption(getHelpOption());
		options.addOption(getConfigurationFileOption());

		return options;
	}

	private static Option getHelpOption()
	{
		final Option option = new Option(
				HelpArgument,
				false,
				"Print this message");
		return option;
	}

	private static Option getConfigurationFileOption()
	{
		final Option option = new Option(
				ConfigurationFileArgument,
				true,
				"The path to the configuration file");
		option.setRequired(true);
		return option;
	}
}
