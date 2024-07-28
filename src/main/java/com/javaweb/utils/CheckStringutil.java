package com.javaweb.utils;

public class CheckStringutil {
	public static boolean isString(String str) {
		if(str != null && !str.equals("")) {
			return true;
		}
		else return false;
	}
}
