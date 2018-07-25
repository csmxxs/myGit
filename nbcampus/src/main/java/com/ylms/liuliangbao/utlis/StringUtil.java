package com.ylms.liuliangbao.utlis;

public class StringUtil {
	private static final char digits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String toHex(byte byteData[]) {
		return toHex(byteData, 0, byteData.length);
	}

	public static String toHex(String data, String encode) {
		try {
			return toHex(data.getBytes(encode));
		} catch (Exception e) {
		}
		return "";
	}

	public static String toHex(byte byteData[], int offset, int len) {
		char buf[] = new char[len * 2];
		int k = 0;
		for (int i = offset; i < len; i++) {
			buf[k++] = digits[(byteData[i] & 255) >> 4];
			buf[k++] = digits[byteData[i] & 15];
		}

		return new String(buf);
	}
}