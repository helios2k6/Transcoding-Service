package com.nlogneg.transcodingService.configuration;

/**
 * Represents a server configuration
 * @author anjohnson
 *
 */
public final class ServerConfiguration{
	private final int portNumber;
	private final boolean help;
	
	/**
	 * Constructs a new Server Configuration object
	 * @param portNumber The port number this server should listen to
	 * @param help Whether or not to show the help text
	 */
	public ServerConfiguration(int portNumber, boolean help){
		this.portNumber = portNumber;
		this.help = help;
	}
	
	/**
	 * Get the port number for this server
	 * @return The port number
	 */
	public int getPortNumber(){
		return portNumber;
	}
	
	public boolean getHelp(){
		return help;
	}
}
