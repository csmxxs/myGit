package com.ylms.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerUtlis {
	/**
	 * 
	 * 验证是否是数字类型的字符串
	 * 
	 * */
	public static boolean RegexString(String id) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) id);
		boolean result = matcher.matches();
		if (result == true) {
			return true;
		} else {
			return false;
		}
	}
}
