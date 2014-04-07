package com.nlogneg.transcodingService.request.incoming;

import java.io.Serializable;

/**
 * Represents the media file selector overrides for a particular request
 * 
 * @author Andrew
 * 
 */
public final class Selector implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5736826441729831051L;

	private final boolean force169AspectRatio;
	private final boolean forceUseAudioTrack;
	private final boolean capResolution;

	private final int audioTrack;
	private final int maxHeight;
	private final int maxWidth;

	/**
	 * @param force169AspectRatio
	 * @param forceUseAudioTrack
	 * @param capResolution
	 * @param audioTrack
	 * @param maxHeight
	 * @param maxWidth
	 */
	public Selector(
			final boolean force169AspectRatio,
			final boolean forceUseAudioTrack,
			final boolean capResolution,
			final int audioTrack,
			final int maxHeight,
			final int maxWidth)
	{

		this.force169AspectRatio = force169AspectRatio;
		this.forceUseAudioTrack = forceUseAudioTrack;
		this.capResolution = capResolution;
		this.audioTrack = audioTrack;
		this.maxHeight = maxHeight;
		this.maxWidth = maxWidth;
	}

	/**
	 * @return the force169AspectRatio
	 */
	public boolean isForce169AspectRatio()
	{
		return this.force169AspectRatio;
	}

	/**
	 * @return the forceUseAudioTrack
	 */
	public boolean isForceUseAudioTrack()
	{
		return this.forceUseAudioTrack;
	}

	/**
	 * @return the capResolution
	 */
	public boolean isCapResolution()
	{
		return this.capResolution;
	}

	/**
	 * @return the audioTrack
	 */
	public int getAudioTrack()
	{
		return this.audioTrack;
	}

	/**
	 * @return the maxHeight
	 */
	public int getMaxHeight()
	{
		return this.maxHeight;
	}

	/**
	 * @return the maxWidth
	 */
	public int getMaxWidth()
	{
		return this.maxWidth;
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
		result = (prime * result) + (this.audioTrack ^ (this.audioTrack >>> 32));
		result = (prime * result) + (this.force169AspectRatio ? 1231 : 1237);
		result = (prime * result) + (this.maxHeight ^ (this.maxHeight >>> 32));
		result = (prime * result) + (this.capResolution ? 1231 : 1237);
		result = (prime * result) + (this.forceUseAudioTrack ? 1231 : 1237);
		result = (prime * result) + (this.maxWidth ^ (this.maxWidth >>> 32));
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
		final Selector other = (Selector) obj;
		if (this.audioTrack != other.audioTrack)
		{
			return false;
		}
		if (this.force169AspectRatio != other.force169AspectRatio)
		{
			return false;
		}
		if (this.maxHeight != other.maxHeight)
		{
			return false;
		}
		if (this.capResolution != other.capResolution)
		{
			return false;
		}
		if (this.forceUseAudioTrack != other.forceUseAudioTrack)
		{
			return false;
		}
		if (this.maxWidth != other.maxWidth)
		{
			return false;
		}
		return true;
	}
}
