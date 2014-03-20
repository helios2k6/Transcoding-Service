package com.nlogneg.transcodingService.configuration;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Proxy that holds the server configuration
 * 
 * @author anjohnson
 * 
 */
public final class ServerConfigurationProxy extends Proxy
{
	public static final String PROXY_NAME = "";

	private ConfigurationFile configurationFile;

	public ServerConfigurationProxy()
	{
		super(PROXY_NAME);
	}

	/**
	 * @return the configurationFile
	 */
	public ConfigurationFile getConfigurationFile()
	{
		return this.configurationFile;
	}

	/**
	 * @param configurationFile
	 *            the configurationFile to set
	 */
	public void setConfigurationFile(final ConfigurationFile configurationFile)
	{
		this.configurationFile = configurationFile;
	}
}
