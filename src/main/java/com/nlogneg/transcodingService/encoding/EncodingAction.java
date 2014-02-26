package com.nlogneg.transcodingService.encoding;

import java.util.EnumSet;


/**
 * Represents the encoding action for some file or track
 * @author anjohnson
 *
 */
public enum EncodingAction{
	Ignore(1 << 0),
	Encode(1 << 1),
	Multiplex(1 << 2);
	
	private final long flag;
	
	private EncodingAction(long flag){
		this.flag = flag;
	}
	
	/**
	 * @return The flag
	 */
	public long getFlag(){
		return flag;
	}
	
	/**
	 * Cycles through and figures out the set of EncodingActions to use
	 * @param flag
	 * @return
	 */
	public static EnumSet<EncodingAction> getFlags(long flag){
		EnumSet<EncodingAction> statusFlags = EnumSet.noneOf(EncodingAction.class);
		
		EncodingAction[] allPossibleActions = EncodingAction.values();
		
		for(EncodingAction ea : allPossibleActions){
			if((flag & ea.getFlag()) == flag){
				statusFlags.add(ea);
			}
		}
		
		return statusFlags;
	}
	
	
	
}
