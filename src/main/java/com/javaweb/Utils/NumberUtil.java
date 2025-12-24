package com.javaweb.Utils;

public class NumberUtil {
	public static boolean isNumber(String value) {
		try {
			long number = Long.parseLong(value);
			}
		
		catch(NumberFormatException ex) {
			return false;
		}
		return true;
		}
}
