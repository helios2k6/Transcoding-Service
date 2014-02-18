package com.nlogneg.transcodingService.media;

import java.io.Serializable;

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
	
	
}
