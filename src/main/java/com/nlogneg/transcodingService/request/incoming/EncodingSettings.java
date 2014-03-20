package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

/**
 * Represents the Encoding Settings to use
 * 
 * @author anjohnson
 * 
 */
public final class EncodingSettings implements Serializable
{
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
	 * 
	 * @param rateControl
	 *            The rate control object
	 * @param estimation
	 *            The estimation object
	 * @param profile
	 *            The profile to use
	 * @param level
	 *            The level to use
	 * @param compatibility
	 *            The compability settings
	 * @param psychoVisualSettings
	 *            The psycho visual settings
	 */
	public EncodingSettings(final RateControl rateControl,
			final Estimation estimation, final Profile profile,
			final Level level, final Compatibility compatibility,
			final PsychoVisualSettings psychoVisualSettings)
	{

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
	public RateControl getRateControl()
	{
		return this.rateControl;
	}

	/**
	 * @return the estimation
	 */
	public Estimation getEstimation()
	{
		return this.estimation;
	}

	/**
	 * @return the profile
	 */
	public Profile getProfile()
	{
		return this.profile;
	}

	/**
	 * @return the level
	 */
	public Level getLevel()
	{
		return this.level;
	}

	/**
	 * @return the compatibility
	 */
	public Compatibility getCompatibility()
	{
		return this.compatibility;
	}

	/**
	 * @return the psychoVisualSettings
	 */
	public PsychoVisualSettings getPsychoVisualSettings()
	{
		return this.psychoVisualSettings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.compatibility == null) ? 0 : this.compatibility
						.hashCode());
		result = (prime * result)
				+ ((this.estimation == null) ? 0 : this.estimation.hashCode());
		result = (prime * result)
				+ ((this.level == null) ? 0 : this.level.hashCode());
		result = (prime * result)
				+ ((this.profile == null) ? 0 : this.profile.hashCode());
		result = (prime * result)
				+ ((this.psychoVisualSettings == null) ? 0
						: this.psychoVisualSettings.hashCode());
		result = (prime * result)
				+ ((this.rateControl == null) ? 0 : this.rateControl.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final EncodingSettings other = (EncodingSettings) obj;
		if (this.compatibility == null)
		{
			if (other.compatibility != null)
			{
				return false;
			}
		} else if (!this.compatibility.equals(other.compatibility))
		{
			return false;
		}
		if (this.estimation == null)
		{
			if (other.estimation != null)
			{
				return false;
			}
		} else if (!this.estimation.equals(other.estimation))
		{
			return false;
		}
		if (this.level == null)
		{
			if (other.level != null)
			{
				return false;
			}
		} else if (!this.level.equals(other.level))
		{
			return false;
		}
		if (this.profile != other.profile)
		{
			return false;
		}
		if (this.psychoVisualSettings == null)
		{
			if (other.psychoVisualSettings != null)
			{
				return false;
			}
		} else if (!this.psychoVisualSettings
				.equals(other.psychoVisualSettings))
		{
			return false;
		}
		if (this.rateControl == null)
		{
			if (other.rateControl != null)
			{
				return false;
			}
		} else if (!this.rateControl.equals(other.rateControl))
		{
			return false;
		}
		return true;
	}

	/**
	 * Convert EncodingSettings to an argument list for x264
	 * 
	 * @param settings
	 * @return
	 */
	public static List<String> convertToArguments(
			final EncodingSettings settings)
	{
		final List<String> rateControlArguments = RateControl
				.convertToArguments(settings.getRateControl());
		final List<String> estimationArguments = Estimation
				.convertToArguments(settings.getEstimation());
		final List<String> profileArguments = Profile
				.convertToArguments(settings.getProfile());
		final List<String> levelArguments = Level.convertToArguments(settings
				.getLevel());
		final List<String> compatibilityArguments = Compatibility
				.convertToArguments(settings.getCompatibility());
		final List<String> psychoArguments = PsychoVisualSettings
				.convertToArguments(settings.getPsychoVisualSettings());

		List<String> arguments = new LinkedList<String>();

		arguments = ListUtils.union(arguments, rateControlArguments);
		arguments = ListUtils.union(arguments, estimationArguments);
		arguments = ListUtils.union(arguments, profileArguments);
		arguments = ListUtils.union(arguments, levelArguments);
		arguments = ListUtils.union(arguments, compatibilityArguments);
		arguments = ListUtils.union(arguments, psychoArguments);

		return arguments;
	}
}
