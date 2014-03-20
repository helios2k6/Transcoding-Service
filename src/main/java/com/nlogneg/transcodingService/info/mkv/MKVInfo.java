package com.nlogneg.transcodingService.info.mkv;

import java.nio.file.Path;
import java.util.List;

/**
 * Holds metadata about an MKV File
 * 
 * @author anjohnson
 * 
 */
public final class MKVInfo
{
	private final Path filePath;
	private final List<Attachment> attachments;

	/**
	 * Creates an MKV Info object
	 * 
	 * @param filePath
	 *            The file path to the MKV file
	 * @param attachments
	 *            The attachments
	 */
	public MKVInfo(final Path filePath, final List<Attachment> attachments)
	{
		this.filePath = filePath;
		this.attachments = attachments;
	}

	/**
	 * @return The file path of the MKV file
	 */
	public Path getFilePath()
	{
		return this.filePath;
	}

	/**
	 * @return the attachments
	 */
	public List<Attachment> getAttachments()
	{
		return this.attachments;
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
				+ ((this.attachments == null) ? 0 : this.attachments.hashCode());
		result = (prime * result)
				+ ((this.filePath == null) ? 0 : this.filePath.hashCode());
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
		final MKVInfo other = (MKVInfo) obj;
		if (this.attachments == null)
		{
			if (other.attachments != null)
			{
				return false;
			}
		} else if (!this.attachments.equals(other.attachments))
		{
			return false;
		}
		if (this.filePath == null)
		{
			if (other.filePath != null)
			{
				return false;
			}
		} else if (!this.filePath.equals(other.filePath))
		{
			return false;
		}
		return true;
	}
}
