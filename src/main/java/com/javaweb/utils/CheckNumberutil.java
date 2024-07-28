package com.javaweb.utils;

public class CheckNumberutil {
	public static boolean isNumber(String str) {
		try {
			Long a = Long.parseLong(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
