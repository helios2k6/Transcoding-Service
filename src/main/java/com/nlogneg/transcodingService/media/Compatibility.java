package com.nlogneg.transcodingService.media;

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
	}
	
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
	
	
}
