package com.nlogneg.transcodingService.configuration;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Holds all of the command line options that were passed to this application
 * 
 * @author anjohnson
 * 
 */
public final class CommandLineOptionsProxy extends Proxy
{
	public static final String PROXY_NAME = "CommandLineOptionsProxy";

	private boolean helpOption;
	private String configurationFile;

	public CommandLineOptionsProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * @return the helpOption
	 */
	public boolean isHelpOption()
	{
		return this.helpOption;
	}

	/**
	 * @param helpOption
	 *            the helpOption to set
	 */
	public void setHelpOption(final boolean helpOption)
	{
		this.helpOption = helpOption;
	}

	/**
	 * @return the configurationFile
	 */
	public String getConfigurationFile()
	{
		return this.configurationFile;
	}

	/**
	 * @param configurationFile
	 *            the configurationFile to set
	 */
	public void setConfigurationFile(final String configurationFile)
	{
		this.configurationFile = configurationFile;
	}
}
