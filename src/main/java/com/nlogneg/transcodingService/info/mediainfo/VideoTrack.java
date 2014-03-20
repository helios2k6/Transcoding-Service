package com.nlogneg.transcodingService.info.mediainfo;

public final class VideoTrack extends MediaTrack
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8828400707334082736L;

	private final String width;
	private final String height;
	private final String displayAspectRatio;
	private final String frameRateMode;
	private final String frameRate;

	/**
	 * Constructs a new video track
	 * 
	 * @param format
	 *            The format
	 * @param codecID
	 *            The codec ID
	 * @param id
	 *            The track ID
	 * @param width
	 *            The width of the video
	 * @param height
	 *            The height of the video
	 * @param displayAspectRatio
	 *            The display aspect ratio
	 * @param frameRateMode
	 *            The frame rate mode
	 * @param frameRate
	 *            The frame rate
	 */
	public VideoTrack(
			final String format,
			final String codecID,
			final int id,
			final String width,
			final String height,
			final String displayAspectRatio,
			final String frameRateMode,
			final String frameRate)
	{
		super(format, codecID, id);
		this.width = width;
		this.height = height;
		this.displayAspectRatio = displayAspectRatio;
		this.frameRateMode = frameRateMode;
		this.frameRate = frameRate;
	}

	/**
	 * Get the width
	 * 
	 * @return The width
	 */
	public String getWidth()
	{
		return this.width;
	}

	/**
	 * Get the height
	 * 
	 * @return The height
	 */
	public String getHeight()
	{
		return this.height;
	}

	/**
	 * Get the display aspect ratio
	 * 
	 * @return The display aspect ratio
	 */
	public String getDisplayAspectRatio()
	{
		return this.displayAspectRatio;
	}

	/**
	 * Get the frame rate mode
	 * 
	 * @return The frame rate mode
	 */
	public String getFrameRateMode()
	{
		return this.frameRateMode;
	}

	/**
	 * Get the frame rate
	 * 
	 * @return The frame rate
	 */
	public String getFrameRate()
	{
		return this.frameRate;
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
		int result = super.hashCode();
		result = (prime * result)
				+ ((this.displayAspectRatio == null) ? 0
						: this.displayAspectRatio.hashCode());
		result = (prime * result)
				+ ((this.frameRate == null) ? 0 : this.frameRate.hashCode());
		result = (prime * result)
				+ ((this.frameRateMode == null) ? 0 : this.frameRateMode
						.hashCode());
		result = (prime * result)
				+ ((this.height == null) ? 0 : this.height.hashCode());
		result = (prime * result)
				+ ((this.width == null) ? 0 : this.width.hashCode());
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
		if (!super.equals(obj))
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		final VideoTrack other = (VideoTrack) obj;
		if (this.displayAspectRatio == null)
		{
			if (other.displayAspectRatio != null)
			{
				return false;
			}
		} else if (!this.displayAspectRatio.equals(other.displayAspectRatio))
		{
			return false;
		}
		if (this.frameRate == null)
		{
			if (other.frameRate != null)
			{
				return false;
			}
		} else if (!this.frameRate.equals(other.frameRate))
		{
			return false;
		}
		if (this.frameRateMode == null)
		{
			if (other.frameRateMode != null)
			{
				return false;
			}
		} else if (!this.frameRateMode.equals(other.frameRateMode))
		{
			return false;
		}
		if (this.height == null)
		{
			if (other.height != null)
			{
				return false;
			}
		} else if (!this.height.equals(other.height))
		{
			return false;
		}
		if (this.width == null)
		{
			if (other.width != null)
			{
				return false;
			}
		} else if (!this.width.equals(other.width))
		{
			return false;
		}
		return true;
	}

	@Override
	public void accept(final TrackVisitor visitor)
	{
		visitor.visit(this);
	}
}
