package com.nlogneg.transcodingService.request.outgoing;

import java.io.Serializable;

/**
 * The different acknowledgement types
 * @author anjohnson
 *
 */
public enum AcknowledgementType implements Serializable{
	SUCCESS,
	FAILURE
}
