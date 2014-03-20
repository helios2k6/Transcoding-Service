package com.nlogneg.transcodingService.request.incoming;

/**
 * Represents a transcoding request.
 * 
 * @author anjohnson
 * 
 */
public final class Request
{

	private final String sourceFile;
	private final String destinationFile;
	private final EncodingSettings encodingSettings;
	private final Selector selector;

	/**
	 * Constructs a new, immutable request
	 * 
	 * @param filePath
	 *            The file path
	 * @param outputPath
	 *            The output path
	 * @param settings
	 *            The encoding settings for this request
	 * @param selector
	 *            The selector
	 */
	protected Request(final String filePath, final String outputPath,
			final EncodingSettings settings, final Selector selector)
	{
		this.sourceFile = filePath;
		this.destinationFile = outputPath;
		this.encodingSettings = settings;
		this.selector = selector;
	}

	/**
	 * @return the selector
	 */
	public Selector getSelector()
	{
		return this.selector;
	}

	/**
	 * Get the file path
	 * 
	 * @return
	 */
	public String getSourceFile()
	{
		return this.sourceFile;
	}

	/**
	 * Get the output path
	 * 
	 * @return
	 */
	public String getDestinationFile()
	{
		return this.destinationFile;
	}

	/**
	 * Gets the encoding settings for this request
	 * 
	 * @return The encoding settings
	 */
	public EncodingSettings getEncodingSettings()
	{
		return this.encodingSettings;
	}

	@Override
	public String toString()
	{
		return "Request[FilePath = " + this.sourceFile + ", OutputPath = "
				+ this.destinationFile + "]";
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
				+ ((this.destinationFile == null) ? 0 : this.destinationFile
						.hashCode());
		result = (prime * result)
				+ ((this.encodingSettings == null) ? 0 : this.encodingSettings
						.hashCode());
		result = (prime * result)
				+ ((this.selector == null) ? 0 : this.selector.hashCode());
		result = (prime * result)
				+ ((this.sourceFile == null) ? 0 : this.sourceFile.hashCode());
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
		final Request other = (Request) obj;
		if (this.destinationFile == null)
		{
			if (other.destinationFile != null)
			{
				return false;
			}
		} else if (!this.destinationFile.equals(other.destinationFile))
		{
			return false;
		}
		if (this.encodingSettings == null)
		{
			if (other.encodingSettings != null)
			{
				return false;
			}
		} else if (!this.encodingSettings.equals(other.encodingSettings))
		{
			return false;
		}
		if (this.selector == null)
		{
			if (other.selector != null)
			{
				return false;
			}
		} else if (!this.selector.equals(other.selector))
		{
			return false;
		}
		if (this.sourceFile == null)
		{
			if (other.sourceFile != null)
			{
				return false;
			}
		} else if (!this.sourceFile.equals(other.sourceFile))
		{
			return false;
		}
		return true;
	}

}
