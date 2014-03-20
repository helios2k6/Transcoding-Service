package com.nlogneg.transcodingService.configuration;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * Represents the configuration file
 * @author anjohnson
 *
 */
public final class ConfigurationFile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5572831847912266818L;
	
	private final Path fontFolder;
	private final int portNumber;
	
	/**
	 * @param fontFolder
	 * @param portNumber
	 */
	public ConfigurationFile(Path fontFolder, int portNumber) {
		this.fontFolder = fontFolder;
		this.portNumber = portNumber;
	}

	/**
	 * @return the fontFolder
	 */
	public Path getFontFolder() {
		return fontFolder;
	}

	/**
	 * @return the portNumber
	 */
	public int getPortNumber() {
		return portNumber;
	}
}
