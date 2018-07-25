package com.ylms.common.utils;

/**
 * 
 * <p>
 * 
 * Cookie工具
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年4月2日 　<br/>
 * 2018年4月2日 <br/>
 * 
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class CookieUtil {

	/**
	 * 获取随机字符串，可用于加密 添加字符，增加保密度
	 */
	private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 获取任意位的随机字符串(0-9 a-z A-Z)
	 * 
	 * @param size
	 *            位数
	 * @return
	 */
	public static final String getRandomNum(int size) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * md5加密(ITS)
	 * 
	 * @param str
	 * @param charSet
	 * @return
	 */
	public synchronized static final String getMD5Str(String str, String charSet) { // md5加密
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			if (charSet == null) {
				messageDigest.update(str.getBytes());
			} else {
				messageDigest.update(str.getBytes(charSet));
			}
		} catch (NoSuchAlgorithmException e) {

		} catch (UnsupportedEncodingException e) {

		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * cookie中的值加密
	 */
	public static String encodeCookie(String value) {
		String encode = "";
		byte bytes[];
		try {
			bytes = value.getBytes("utf-8");
			encode = encode_base64(bytes, bytes.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encode;
	}

	/**
	 * cookie中值解密
	 */
	public static String decodeCookie(String value) {
		byte bytes[];
		String decode = "";
		try {
			bytes = decode_base64(value, 11);
			decode = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decode;
	}

	static private final char base64_code[] = { '.', '/', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9' };

	// Table for Base64 decoding
	static private final byte index_64[] = { -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, 0, 1, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -1,
			-1, -1, -1, -1, -1, -1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, -1, -1, -1, -1,
			-1, -1, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
			43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, -1, -1, -1, -1, -1 };

	private static String encode_base64(byte d[], int len)
			throws IllegalArgumentException {
		int off = 0;
		StringBuffer rs = new StringBuffer();
		int c1, c2;

		if (len <= 0 || len > d.length)
			throw new IllegalArgumentException("Invalid len");

		while (off < len) {
			c1 = d[off++] & 0xff;
			rs.append(base64_code[(c1 >> 2) & 0x3f]);
			c1 = (c1 & 0x03) << 4;
			if (off >= len) {
				rs.append(base64_code[c1 & 0x3f]);
				break;
			}
			c2 = d[off++] & 0xff;
			c1 |= (c2 >> 4) & 0x0f;
			rs.append(base64_code[c1 & 0x3f]);
			c1 = (c2 & 0x0f) << 2;
			if (off >= len) {
				rs.append(base64_code[c1 & 0x3f]);
				break;
			}
			c2 = d[off++] & 0xff;
			c1 |= (c2 >> 6) & 0x03;
			rs.append(base64_code[c1 & 0x3f]);
			rs.append(base64_code[c2 & 0x3f]);
		}
		return rs.toString();
	}

	private static byte char64(char x) {
		if ((int) x < 0 || (int) x > index_64.length)
			return -1;
		return index_64[(int) x];
	}

	private static byte[] decode_base64(String s, int maxolen)
			throws IllegalArgumentException {
		StringBuffer rs = new StringBuffer();
		int off = 0, slen = s.length(), olen = 0;
		byte ret[];
		byte c1, c2, c3, c4, o;

		if (maxolen <= 0)
			throw new IllegalArgumentException("Invalid maxolen");
		while (off < slen - 1 && olen < maxolen) {
			c1 = char64(s.charAt(off++));
			c2 = char64(s.charAt(off++));
			if (c1 == -1 || c2 == -1)
				break;
			o = (byte) (c1 << 2);
			o |= (c2 & 0x30) >> 4;
			rs.append((char) o);
			if (++olen >= maxolen || off >= slen)
				break;
			c3 = char64(s.charAt(off++));
			if (c3 == -1)
				break;
			o = (byte) ((c2 & 0x0f) << 4);
			o |= (c3 & 0x3c) >> 2;
			rs.append((char) o);
			if (++olen >= maxolen || off >= slen)
				break;
			c4 = char64(s.charAt(off++));
			o = (byte) ((c3 & 0x03) << 6);
			o |= c4;
			rs.append((char) o);
			++olen;
		}

		ret = new byte[olen];
		for (off = 0; off < olen; off++)
			ret[off] = (byte) rs.charAt(off);
		return ret;
	}

	public static void main(String[] args) {
		String v = encodeCookie("15890620797");
		// [97, 100, 109, 105, 110]
		// [97, 100, 109, 105, 110]
		// String tel=decodeCookie("WUPrYU2");
		String tel = decodeCookie(v);
		System.out.println(tel);
	}

}
