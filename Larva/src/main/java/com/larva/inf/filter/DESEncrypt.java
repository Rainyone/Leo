package com.larva.inf.filter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DESEncrypt {
	private static String _KEY = "bec99deaaf19c593613ffbcc6250329d";
	private static String shaKeyStr = "charge";

	public static String EncryptBody(String response) {
		try {
			byte[] encryptParas = DESEncrypt.encrypt(response.getBytes());
			return Base64.encodeBase64String(encryptParas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(key);

		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		cipher.init(1, securekey, sr);

		return cipher.doFinal(src);
	}

	public static byte[] encrypt(byte[] src) {
		try {
			return encrypt(src, _KEY.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(key);

		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		cipher.init(2, securekey, sr);

		return cipher.doFinal(src);
	}

	public static byte[] decrypt(byte[] src) throws Exception {
		return decrypt(src, _KEY.getBytes());
	}

	public static String showByteArray(byte[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		byte[] arrayOfByte = data;
		int j = data.length;
		for (int i = 0; i < j; i++) {
			byte b = arrayOfByte[i];
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	private static String reverse(String s) {
		return new StringBuffer(s).reverse().toString();
	}

	public static String getSign(String appId, String appSecret,
			String timestamp) {
		String sign = appId + reverse(appSecret) + timestamp + shaKeyStr;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(sign.getBytes());
			String sha1 = Base64.encodeBase64String(md.digest());
			return sha1;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sign;
	}
}