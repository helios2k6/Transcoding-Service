package com.nlogneg.transcodingService.configuration;

import org.puremvc.java.multicore.patterns.proxy.Proxy;

public class ServerConfigurationProxy extends Proxy {
	
	public static final String PROXY_NAME = "ServerConfigurationProxy";
	
	private ServerConfiguration serverConfiguration;
	
	public ServerConfigurationProxy() {
		super(PROXY_NAME);
	}
	
	/**
	 * Gets the Server Configuration object
	 * @return
	 */
	public ServerConfiguration getServerConfiguration() {
		return serverConfiguration;
	}

	/**
	 * Sets the Server Configuration object
	 * @param serverConfiguration
	 */
	public void setServerConfiguration(ServerConfiguration serverConfiguration) {
		this.serverConfiguration = serverConfiguration;
	}
}
