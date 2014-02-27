package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the Encoding Settings to use 
 * @author anjohnson
 *
 */
public final class EncodingSettings implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8003203963489899114L;
	
	private final RateControl rateControl;
	private final Estimation estimation;
	private final Profile profile;
	private final Level level;
	private final Compatibility compatibility;
	private final PsychoVisualSettings psychoVisualSettings;
	
	
	/** 
	 * Constructs a new EncodingSettings object
	 * @param rateControl The rate control object
	 * @param estimation The estimation object
	 * @param profile The profile to use
	 * @param level The level to use
	 * @param compatibility The compability settings
	 * @param psychoVisualSettings The psycho visual settings
	 */
	public EncodingSettings(
			RateControl rateControl, 
			Estimation estimation,
			Profile profile, 
			Level level,
			Compatibility compatibility,
			PsychoVisualSettings psychoVisualSettings){
		
		this.rateControl = rateControl;
		this.estimation = estimation;
		this.profile = profile;
		this.level = level;
		this.compatibility = compatibility;
		this.psychoVisualSettings = psychoVisualSettings;
	}


	/**
	 * @return the rateControl
	 */
	public RateControl getRateControl() {
		return rateControl;
	}


	/**
	 * @return the estimation
	 */
	public Estimation getEstimation() {
		return estimation;
	}


	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}


	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}


	/**
	 * @return the compatibility
	 */
	public Compatibility getCompatibility() {
		return compatibility;
	}


	/**
	 * @return the psychoVisualSettings
	 */
	public PsychoVisualSettings getPsychoVisualSettings() {
		return psychoVisualSettings;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((compatibility == null) ? 0 : compatibility.hashCode());
		result = prime * result
				+ ((estimation == null) ? 0 : estimation.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime
				* result
				+ ((psychoVisualSettings == null) ? 0 : psychoVisualSettings
						.hashCode());
		result = prime * result
				+ ((rateControl == null) ? 0 : rateControl.hashCode());
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
		EncodingSettings other = (EncodingSettings) obj;
		if (compatibility == null) {
			if (other.compatibility != null) {
				return false;
			}
		} else if (!compatibility.equals(other.compatibility)) {
			return false;
		}
		if (estimation == null) {
			if (other.estimation != null) {
				return false;
			}
		} else if (!estimation.equals(other.estimation)) {
			return false;
		}
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.equals(other.level)) {
			return false;
		}
		if (profile != other.profile) {
			return false;
		}
		if (psychoVisualSettings == null) {
			if (other.psychoVisualSettings != null) {
				return false;
			}
		} else if (!psychoVisualSettings.equals(other.psychoVisualSettings)) {
			return false;
		}
		if (rateControl == null) {
			if (other.rateControl != null) {
				return false;
			}
		} else if (!rateControl.equals(other.rateControl)) {
			return false;
		}
		return true;
	}
	
	public static List<String> convertToArguments(EncodingSettings settings){
		List<String> rateControlArguments = RateControl.convertToArguments(settings.getRateControl());
		
		
		return rateControlArguments;
	}
}
