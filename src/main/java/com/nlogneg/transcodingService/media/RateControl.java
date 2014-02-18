package com.nlogneg.transcodingService.media;

/**
 * Represents the rate control setting for Encoding settings
 * @author anjohnson
 *
 */
public class RateControl{
	
	/**
	 * The type of rate control to use
	 * @author anjohnson
	 *
	 */
	public enum Type{
		CRF,
		QP,
		BIT_RATE
	}
	
	private final Type type;
	private final String setting;
	
	/**
	 * Constructs a new Rate Control object
	 * @param type The type of rate control
	 * @param setting The setting for the rate control
	 */
	public RateControl(Type type, String setting){
		this.type = type;
		this.setting = setting;
	}
	
	/**
	 * Get the type of rate control to use
	 * @return The type of rate control
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Get the setting for the rate control
	 * @return The setting
	 */
	public String getSetting() {
		return setting;
	}
	
}
