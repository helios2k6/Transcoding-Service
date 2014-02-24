package com.nlogneg.transcodingService.configuration;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.constants.Notifications;

/**
 * Configures the server
 * @author anjohnson
 *
 */
public class ConfigureServerCommand extends SimpleCommand{

	private static final Logger Log = LogManager.getLogger(ConfigureServerCommand.class);
	
	@Override
	public void execute(INotification notification){
		if(notification.getBody() == null){
			fail("Could not configure server. Body is null");
		}

		if(notification.getBody().getClass() != String[].class){
			fail("Could not configure server. Body is not a String array.");
		}

		String[] args = (String[])notification.getBody();

		if(args.length < 1){
			fail("Could not configure server. Not enough arguments were passed in");
		}

		ServerConfiguration serverConfiguration = parseServerConfiguration(args);
		setServerConfiguration(serverConfiguration);
	}

	/**
	 * Sets the server configuration
	 * @param portNumber The port number
	 */
	private void setServerConfiguration(ServerConfiguration serverConfiguration){
		Facade facade = getFacade();
		ServerConfigurationProxy proxy = (ServerConfigurationProxy)facade.retrieveProxy(ServerConfigurationProxy.PROXY_NAME);
		proxy.setServerConfiguration(serverConfiguration);
	}

	/**
	 * Sets the failure signal 
	 * @param message The message to associate with this failure
	 */
	private void fail(String message){
		fail(message, null);
	}

	/**
	 * Sets the failure signal with an exception
	 * @param message The message to associate with this failure
	 * @param ex The exception to associate with this failure
	 */
	private void fail(String message, Exception ex){
		Log.error(message, ex);
		sendNotification(Notifications.PrintHelpAndExitMacroCommandNotification);
	}
	
	/**
	 * Parses the command line arguments
	 * @param args The command line arguments
	 * @return A new server configuration
	 */
	private ServerConfiguration parseServerConfiguration(String[] args){
		Options standardOptions = CommandLineOptionsFactory.createOptions();
		CommandLineParser parser = new BasicParser();
		try{
			CommandLine commandLine = parser.parse(standardOptions, args);

			boolean needsHelp = commandLine.hasOption(CommandLineOptionsFactory.HelpArgument);

			String portNumberString = commandLine.getOptionValue(CommandLineOptionsFactory.PortNumberArgument);
			int parsedPortNumber = parsePortNumber(portNumberString);
			
			String fontFolder = commandLine.getOptionValue(CommandLineOptionsFactory.FontFolderArgument);
			Path fontPath = parseFontPath(fontFolder);
			return new ServerConfiguration(parsedPortNumber, needsHelp, fontPath);
		}catch(ParseException exception){
			fail("Could not parse command line arguments.", exception);
		}catch(InvalidPathException exception){
			fail("Could not parse path.", exception);
		}
		
		fail("An impossible evolution has occured.");
		return null;
	}

	private Path parseFontPath(String fontFolder){
		return Paths.get(fontFolder);
	}
	
	/**
	 * Parses the port number
	 * @param portNumberArgument The string representation of the port number
	 * @return The port number
	 */
	private int parsePortNumber(String portNumberArgument){
		try{
			int portNumber = Integer.parseInt(portNumberArgument);

			if(portNumber < 300 || portNumber > 60000){
				fail("Port number is too low or too high. Port number must be between 300 and 60000");
			}
		}catch(NumberFormatException e){
			fail("Port number could not be parsed.", e);
		}
		
		fail("An impossible evolution has occured.");
		
		// This should never happen. The fail message should shut down the system
		return -1;
	}
}
