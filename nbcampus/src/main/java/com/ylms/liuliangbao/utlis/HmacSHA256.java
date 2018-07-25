package com.ylms.liuliangbao.utlis;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class HmacSHA256{

	public static String encodeHmacSHA256(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte[] digest = mac.doFinal(data);
        return (new HexBinaryAdapter()).marshal(digest);
    }
	
	/**
	 * 根据接口请求参数生成HMACSHA256签名 plainText
	 * 
	 * @param plainText
	 * @param appSecret
	 * @return
	 */
	public static String generateHmacSha256Signature(String plainText,String signSecret) {
		String signature = null;
		try {
			byte[] data = ByteFormat.hexToBytes(ByteFormat.toHex(plainText.getBytes()));
			byte[] key = ByteFormat.hexToBytes(ByteFormat.toHex(signSecret.getBytes()));
			signature = encodeHmacSHA256(data, key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return signature;
	}
	
	public static void main(String[] args) {
		String code = "80134178031505898056000v1.046C3BD7168385FEF478CD8D2A3BB8F7C563EC5E8C23F6728ADEC8C6B0DFA81883CBA99E53EFDF46530F372C6D51BBE330B1F7E5331C1523EF51A181B1E5F02B0CFBE3354EEB245D6336FDE663B8F94778D2ED9249487F6E9EB624E3BF57FF532AF22AD0F66EE185997670D79C140237C";
		String secretKey = "XmZq66DlOopqGvylNUedbFQ8SA";

		System.out.println(generateHmacSha256Signature(code,secretKey));
	}

}
