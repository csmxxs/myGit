package com.ylms.liuliangbao.utlis;

public class XXTea {
	public XXTea() {
	}

	public static String encrypt(String plain, String charset, String hexKey)
			throws Exception {
		return plain != null && charset != null && hexKey != null ? ByteFormat
				.toHex(encrypt(plain.getBytes(charset),
						ByteFormat.hexToBytes(hexKey))) : null;
	}

	public static String decrypt(String cipherHex, String charset, String hexKey)
			throws Exception {
		return cipherHex != null && charset != null && hexKey != null ? new String(
				decrypt(ByteFormat.hexToBytes(cipherHex),
						ByteFormat.hexToBytes(hexKey)), charset) : null;
	}

	/**
	 * Encrypt data with key.
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] encrypt(byte[] plainData, byte[] key) {
		return plainData != null && plainData.length != 0 && key != null ? toByteArray(
				encrypt(toIntArray(plainData, true), toIntArray(key, false)),
				false) : null;
	}

	/**
	 * Decrypt data with key.
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] decrypt(byte[] cipherData, byte[] key) {
		return cipherData != null && cipherData.length != 0 && key != null ? toByteArray(
				decrypt(toIntArray(cipherData, false), toIntArray(key, false)),
				true) : null;
	}

	/**
	 * Encrypt data with key.
	 * 
	 * @param v
	 * @param k
	 * @return
	 */
	public static int[] encrypt(int[] v, int[] k) {
		int n = v.length;

		int y;
		int p;
		int rounds = 6 + 52 / n;
		int sum = 0;
		int z = v[n - 1];
		int delta = 0x9E3779B9;
		do {
			sum += delta;
			int e = (sum >>> 2) & 3;
			for (p = 0; p < n - 1; p++) {
				y = v[p + 1];
				z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
						+ (k[p & 3 ^ e] ^ z);
			}
			y = v[0];
			z = v[n - 1] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
					+ (k[p & 3 ^ e] ^ z);
		} while (--rounds > 0);

		return v;
	}

	/**
	 * Decrypt data with key.
	 * 
	 * @param v
	 * @param k
	 * @return
	 */
	public static int[] decrypt(int[] v, int[] k) {
		int n = v.length;
		int z = v[n - 1], y = v[0], delta = 0x9E3779B9, sum, e;
		int p;
		int rounds = 6 + 52 / n;
		sum = rounds * delta;
		y = v[0];
		do {
			e = (sum >>> 2) & 3;
			for (p = n - 1; p > 0; p--) {
				z = v[p - 1];
				y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
						+ (k[p & 3 ^ e] ^ z);
			}
			z = v[n - 1];
			y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
					+ (k[p & 3 ^ e] ^ z);
		} while ((sum -= delta) != 0);
		return v;
	}

	/**
	 * Convert byte array to int array.
	 * 
	 * @param data
	 * @param includeLength
	 * @return
	 */
	private static int[] toIntArray(byte[] data, boolean includeLength) {
		int n = (((data.length & 3) == 0) ? (data.length >>> 2)
				: ((data.length >>> 2) + 1));
		int[] result;

		if (includeLength) {
			result = new int[n + 1];
			result[n] = data.length;
		} else {
			result = new int[n];
		}
		n = data.length;
		for (int i = 0; i < n; i++) {
			result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
		}
		return result;
	}

	/**
	 * Convert int array to byte array.
	 * 
	 * @param data
	 * @param includeLength
	 * @return
	 */
	private static byte[] toByteArray(int[] data, boolean includeLength) {
		int n = data.length << 2;

		;
		if (includeLength) {
			int m = data[data.length - 1];

			if (m > n) {
				return null;
			} else {
				n = m;
			}
		}
		byte[] result = new byte[n];

		for (int i = 0; i < n; i++) {
			result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
		}
		return result;
	}

	private final static char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String toHex(byte[] buf) {
		if (buf == null || buf.length == 0) {
			return "";
		}
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < buf.length; i++) {
			out.append(HEX[(buf[i] >> 4) & 0x0f]).append(HEX[buf[i] & 0x0f]);
		}
		return out.toString();
	}

	public static void main(String[] args) {
		try {
			System.out.println(XXTea.encrypt(
					"mobile=18126751884&requestNo=152099843753271979&taskId=100007782&coin=1", "UTF-8",
					toHex("rffhoiagTgoiYkgiae6Kausng12r".getBytes())));
			// 60FED4BCEDE6D9A23388E19AC05916AF25E28137D7DF9DE5D7099CFEE3CE48A5B6A0BF5571E30732
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
