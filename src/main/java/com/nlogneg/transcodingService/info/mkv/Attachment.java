package com.nlogneg.transcodingService.info.mkv;

import javax.activation.MimeType;

import com.nlogneg.transcodingService.utilities.MimeTypeUtilities;

/**
 * Represents an MKV Attachment
 * @author anjohnson
 *
 */
public final class Attachment{
	private final long id;
	private final String fileName;
	private final MimeType mimeType;
	private final long uid;
	
	/**
	 * Creates an Attachment
	 * @param id The track ID
	 * @param fileName The file name
	 * @param mimeType The MIME type
	 * @param uid The attachment UID
	 */
	public Attachment(long id, String fileName, MimeType mimeType, long uid){
		this.id = id;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.uid = uid;
	}

	/**
	 * @return the id
	 */
	public long getId(){
		return id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName(){
		return fileName;
	}

	/**
	 * @return the mimeType
	 */
	public MimeType getMimeType(){
		return mimeType;
	}

	/**
	 * @return the uid
	 */
	public long getUid(){
		return uid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + new Long(id).hashCode();
		result = prime * result + (int) (uid ^ (uid >>> 32));
		result = prime * result + mimeType.getBaseType().hashCode();
		result = prime * result + mimeType.getSubType().hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Attachment other = (Attachment) obj;
		if (fileName == null){
			if (other.fileName != null){
				return false;
			}
		}else if (!fileName.equals(other.fileName)){
			return false;
		}
		if (id != other.id){
			return false;
		}
		if (uid != other.uid){
			return false;
		}
		//Check MIME Type
		if(MimeTypeUtilities.areEqual(mimeType, other.mimeType) == false){
			return false;
		}
		
		return true;
	}
	
	
	
}
