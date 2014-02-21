package com.nlogneg.transcodingService.configuration;

/**
 * Represents a server configuration
 * @author anjohnson
 *
 */
public final class ServerConfiguration{
	private final int portNumber;
	private final boolean help;
	private final String fontFolder;
	
	/**
	 * Constructs a new Server Configuration object
	 * @param portNumber The port number this server should listen to
	 * @param help Whether or not to show the help text
	 * @param fontFolder Where to install fonts
	 */
	public ServerConfiguration(int portNumber, boolean help, String fontFolder){
		this.portNumber = portNumber;
		this.help = help;
		this.fontFolder = fontFolder;
	}

	/**
	 * @return the portNumber
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/**
	 * @return the help
	 */
	public boolean isHelp() {
		return help;
	}

	/**
	 * @return the fontFolder
	 */
	public String getFontFolder() {
		return fontFolder;
	}
}
