package com.ylms.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.crypto.Cipher;

import org.apache.shiro.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtil {
	// 字符串进行加密算法的名称
	public static final String ALGORITHM = "RSA";
	// 字符串进行加密填充的名称
	public static final String PADDING = "RSA/NONE/NoPadding";
	// 字符串持有安全提供者的名称
	public static final String PROVIDER = "BC";
	// 私钥文件路径
	public static final String PRIVATE_KEY_FILE = RSAUtil.class
			.getClassLoader().getResource("").getPath()
			+ "key" + "privatekey.key";
	// 公钥文件路径
	public static final String PUBLIC_KEY_FILE = RSAUtil.class.getClassLoader()
			.getResource("").getPath()
			+ "key" + "publickey.key";
	private static final Logger log = LoggerFactory.getLogger(RSAUtil.class);

	/**
	 * 测试加密解密
	 */
	public void rsaTest(String str) {
		// log.info("[要加密解密的参数:{}]", str);
		// try {
		// String cipherText = encrypt(str);
		// String plainText = decrypt(cipherText);
		// log.info("[加密后的参数为:{}]", cipherText);
		// log.info("[解密后的参数为:{}]", plainText);
		// } catch (Exception e) {
		// log.info("[RSA加密解密出现异常:{}]", e);
		// }
	}

	/**
	 * 将字符串进行RSA加密
	 * 
	 * @param text
	 * @return
	 */
	@SuppressWarnings({ "resource", "unused" })
	public static String encrypt(String text) {
		String cipherTextBase64 = "";
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					new FileInputStream(PUBLIC_KEY_FILE));
			PublicKey publicKey = (PublicKey) inputStream.readObject();
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] cipherText = cipher.doFinal(text.getBytes());
			Base64 base64 = new Base64();
			cipherTextBase64 = Base64.encodeToString(cipherText);
		} catch (Exception e) {
			log.info("[字符串进行RSA加密出现异常:{}]", e);
		}
		return cipherTextBase64;
	}

	/**
	 * 将字符串进行RSA解密
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings({ "resource", "unused" })
	public static String decrypt(String str) {

		// 判断密钥文件是否存在
		if (!RSAUtil.areKeysPresent()) {
			// 不存在则创建
			RSAUtil.generateKey();
		}
		byte[] dectyptedText = null;
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					new FileInputStream(PRIVATE_KEY_FILE));
			PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			Base64 base64 = new Base64();
			byte[] text = Base64.decode(str);
			dectyptedText = cipher.doFinal(text);
			return new String(dectyptedText);
		} catch (Exception e) {
			log.info("[字符串进行RSA解密出现异常:{}]", e);
			return null;
		}
	}

	/**
	 * 判断秘钥文件是否存在
	 * 
	 * @return
	 */
	public static boolean areKeysPresent() {
		File privateKey = new File(PRIVATE_KEY_FILE);
		File publicKey = new File(PUBLIC_KEY_FILE);
		if (privateKey.exists() && publicKey.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 生成公钥文件和私钥文件
	 */
	public static void generateKey() {

		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(
					ALGORITHM, PROVIDER);
			keyGen.initialize(1024);
			final KeyPair key = keyGen.generateKeyPair();
			File privateKeyFile = new File(PRIVATE_KEY_FILE);
			File publicKeyFile = new File(PUBLIC_KEY_FILE);
			if (privateKeyFile.getParentFile() != null) {
				privateKeyFile.getParentFile().mkdirs();
			}
			privateKeyFile.createNewFile();
			if (publicKeyFile.getParentFile() != null) {
				publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();
			ObjectOutputStream publicKeyOS = new ObjectOutputStream(
					new FileOutputStream(publicKeyFile));
			publicKeyOS.writeObject(key.getPublic());
			publicKeyOS.close();
			ObjectOutputStream privateKeyOS = new ObjectOutputStream(
					new FileOutputStream(privateKeyFile));
			privateKeyOS.writeObject(key.getPrivate());
			privateKeyOS.close();
		} catch (Exception e) {
			log.info("[生成公钥文件和私钥文件出现异常{}]", e);
		}
	}
}
