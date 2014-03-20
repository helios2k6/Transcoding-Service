package com.nlogneg.transcodingService.info.mkv;

import javax.activation.MimeType;

import com.nlogneg.transcodingService.utilities.MimeTypeUtilities;

/**
 * Represents an MKV Attachment
 * 
 * @author anjohnson
 * 
 */
public final class Attachment
{
	private final long id;
	private final String fileName;
	private final MimeType mimeType;
	private final long uid;

	/**
	 * Creates an Attachment
	 * 
	 * @param id
	 *            The track ID
	 * @param fileName
	 *            The file name
	 * @param mimeType
	 *            The MIME type
	 * @param uid
	 *            The attachment UID
	 */
	public Attachment(
			final long id,
			final String fileName,
			final MimeType mimeType,
			final long uid)
	{
		this.id = id;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.uid = uid;
	}

	/**
	 * @return the id
	 */
	public long getId()
	{
		return this.id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return this.fileName;
	}

	/**
	 * @return the mimeType
	 */
	public MimeType getMimeType()
	{
		return this.mimeType;
	}

	/**
	 * @return the uid
	 */
	public long getUid()
	{
		return this.uid;
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
				+ ((this.fileName == null) ? 0 : this.fileName.hashCode());
		result = (prime * result) + new Long(this.id).hashCode();
		result = (prime * result) + (int) (this.uid ^ (this.uid >>> 32));
		result = (prime * result) + this.mimeType.getBaseType().hashCode();
		result = (prime * result) + this.mimeType.getSubType().hashCode();
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
		final Attachment other = (Attachment) obj;
		if (this.fileName == null)
		{
			if (other.fileName != null)
			{
				return false;
			}
		} else if (!this.fileName.equals(other.fileName))
		{
			return false;
		}
		if (this.id != other.id)
		{
			return false;
		}
		if (this.uid != other.uid)
		{
			return false;
		}
		// Check MIME Type
		if (MimeTypeUtilities.areEqual(this.mimeType, other.mimeType) == false)
		{
			return false;
		}

		return true;
	}

}
