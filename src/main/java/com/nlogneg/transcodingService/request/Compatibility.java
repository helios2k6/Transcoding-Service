package com.nlogneg.transcodingService.request;

import java.io.Serializable;

/**
 * Constructs a new Compatibility object
 * @author anjohnson
 *
 */
public final class Compatibility implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6880522410686420605L;

	private final SampleAspectRatio sampleAspectRatio;
	private final boolean useAccessUnitDelimiters;
	
	/**
	 * Construct a new compatibility object
	 * @param sampleAspectRatio The sample aspect ratio
	 * @param useAccessUnitDelimiters Whether to use access unit delimiters
	 */
	public Compatibility(SampleAspectRatio sampleAspectRatio, boolean useAccessUnitDelimiters) {
		this.sampleAspectRatio = sampleAspectRatio;
		this.useAccessUnitDelimiters = useAccessUnitDelimiters;
	}

	/**
	 * @return the sampleAspectRatio
	 */
	public SampleAspectRatio getSampleAspectRatio() {
		return sampleAspectRatio;
	}

	/**
	 * @return the useAccessUnitDelimiters
	 */
	public boolean isUseAccessUnitDelimiters() {
		return useAccessUnitDelimiters;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((sampleAspectRatio == null) ? 0 : sampleAspectRatio
						.hashCode());
		result = prime * result + (useAccessUnitDelimiters ? 1231 : 1237);
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
		Compatibility other = (Compatibility) obj;
		if (sampleAspectRatio == null) {
			if (other.sampleAspectRatio != null) {
				return false;
			}
		} else if (!sampleAspectRatio.equals(other.sampleAspectRatio)) {
			return false;
		}
		if (useAccessUnitDelimiters != other.useAccessUnitDelimiters) {
			return false;
		}
		return true;
	}
	
	
}
