package com.nlogneg.transcodingService.media;

import java.io.Serializable;

public final class SampleAspectRatio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3835516724765000925L;

	private final int width;
	private final int height;

	public SampleAspectRatio(int width, int height){
		this.width = width;
		this.height = height;
	}

	/**
	 * Get the width
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the height
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
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
		SampleAspectRatio other = (SampleAspectRatio) obj;
		if (height != other.height) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		return true;
	}
	
	
}
