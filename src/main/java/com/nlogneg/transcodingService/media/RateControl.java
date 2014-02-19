package com.nlogneg.transcodingService.media;

import java.io.Serializable;

/**
 * Represents the rate control setting for Encoding settings
 * @author anjohnson
 *
 */
public class RateControl implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3650899713022263523L;

	/**
	 * The type of rate control to use
	 * @author anjohnson
	 *
	 */
	public enum Type{
		ConstantRateFactor,
		QuantizationParameter,
		BitRate,
	}
	
	private final Type type;
	private final double setting;
	
	/**
	 * Constructs a new Rate Control object
	 * @param type The type of rate control
	 * @param setting The setting for the rate control
	 */
	public RateControl(Type type, double setting){
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
	public double getSetting() {
		return setting;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(setting);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RateControl other = (RateControl) obj;
		if (Double.doubleToLongBits(setting) != Double
				.doubleToLongBits(other.setting)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}
	
	
}
