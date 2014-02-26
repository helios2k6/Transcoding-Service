package com.nlogneg.transcodingService.encoding;

import com.nlogneg.transcodingService.encoding.mkv.MKVEncodingJob;

/**
 * Represents an EncodingJob visitor
 * @author anjohnson
 *
 */
public interface EncodingJobVisitor{
	void visit(MKVEncodingJob job);
}
