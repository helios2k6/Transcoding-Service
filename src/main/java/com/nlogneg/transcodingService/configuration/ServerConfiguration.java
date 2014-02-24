package com.nlogneg.transcodingService.configuration;


/**
 * Represents a server configuration
 * @author anjohnson
 *
 */
public final class ServerConfiguration{
	private final int portNumber;
	private final boolean help;
	private final FontStrategyConfiguration fontConfiguration;
	
	/**
	 * Constructs a new Server Configuration object
	 * @param portNumber The port number this server should listen to
	 * @param help Whether or not to show the help text
	 * @param fontConfiguration The font configuration
	 */
	public ServerConfiguration(int portNumber, boolean help, FontStrategyConfiguration fontConfiguration){
		this.portNumber = portNumber;
		this.help = help;
		this.fontConfiguration = fontConfiguration;
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
	 * @return the fontConfiguration
	 */
	public FontStrategyConfiguration getFontConfiguration() {
		return fontConfiguration;
	}
}

