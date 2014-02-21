package com.nlogneg.transcodingService.configuration;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Constructs command line options used to parse the command line arguments
 * @author anjohnson
 *
 */
public class CommandLineOptionsFactory{
	public static final String PortNumberArgument = "port-number";
	public static final String PortNumberArgumentShort = "p";
	public static final String HelpArgument = "help";
	public static final String HelpArgumentShort = "h";
	public static final String FontFolderArgument = "font-folder";
	public static final String FontFolderArgumentShort = "ff";
	
	/**
	 * Construct standard command line options for argument parsing
	 * @return A new set of options
	 */
	public static Options createOptions(){
		Option portNumberOption = createPortNumberOption();
		Option helpOption = createHelpOption();
		Option fontFolderOption = createFontFolderOption();
		
		Options options = new Options();
		options.addOption(portNumberOption);
		options.addOption(helpOption);
		options.addOption(fontFolderOption);
		
		return options;
	}
	
	/**
	 * Creates the port-number command line option
	 * @return The port number option
	 */
	private static Option createPortNumberOption(){
		Option portNumberOption = new Option(PortNumberArgument, PortNumberArgument, true, "The port number this server should listen to for requests.");
		portNumberOption.setRequired(true);

		return portNumberOption;
	}
	
	/**
	 * Creates the help command line option
	 * @return The help option
	 */
	private static Option createHelpOption(){
		Option help = new Option(HelpArgumentShort, HelpArgument, false, "Print this message.");
		help.setRequired(false);
		
		return help;
	}
	
	/**
	 * Creates the font-folder command line option
	 * @return The font-folder command line option
	 */
	private static Option createFontFolderOption(){
		Option fontFolder = new Option(FontFolderArgumentShort, FontFolderArgument, true, "Specify where to install font attachments");
		fontFolder.setRequired(true);
		
		return fontFolder;
	}
}
