package com.nlogneg.transcodingService.utilities.math;

import com.nlogneg.transcodingService.utilities.Optional;

public final class IntegerUtils{
	/**
	 * Gets the GCD of two longs
	 * @param a
	 * @param b
	 * @return The GCD. Buffer-overflow not detected
	 */
	public static long gcd(long a, long b){
		if(a == b){
			return a;
		}
		
		long larger = a;
		long smaller = b;
		
		if(a < b){
			larger = b;
			smaller = a;
		}
		
		long remainder = larger / smaller;
		
		if(remainder == 0){
			return smaller;
		}
		
		if(remainder == 1){
			return 1;
		}
		
		return gcd(smaller, remainder);
	}
	
	/**
	 * Gets the GCD of two integers. 
	 * @param a
	 * @param b
	 * @return The GCD or -1 if buffer-overflow is detected
	 */
	public static int gcd(int a, int b){
		long longResult = gcd((long)a, (long)b);
		if(longResult > (long)Integer.MAX_VALUE){
			return -1;
		}
		
		return (int)longResult;
	}
	
	/**
	 * Try to cast a string to an Integer
	 * @param input
	 * @return
	 */
	public static Optional<Integer> tryCastStringToInteger(String input){
		try{
			Integer i = Integer.parseInt(input);
			return Optional.make(i);
		}catch(NumberFormatException e){
			return Optional.none();
		}
	}
}
