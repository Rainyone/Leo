package com.larva.inf.filter;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.URIUtil;

public class DESEncrypt {
	private static String KEY = "bec99deaaf19c593613ffbcc6250329d";
	public static String SHA_KEY = "charge";

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
			return encrypt(src, KEY.getBytes());
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
		return decrypt(src, KEY.getBytes());
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

	public static String getSign(String appId, String appSecret,String imei,
			String timestamp) {
		String sign = appId + reverse(appSecret) +imei+ SHA_KEY + timestamp ;
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
	public static String getAppInfLogSign(String charge_key, String imsi,String stepname,
			String timestamp) {
		String sign = charge_key + reverse(imsi) +stepname+ SHA_KEY + timestamp ;
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
	public static String getDesUrl(String url){
		String encode = Base64.encodeBase64String(DESEncrypt.encrypt(url.getBytes()));
		return URLEncoder.encode(encode);
	}
	
	public static void main(String[] args) {
		String url = "setCharge?app_id=6&app_key=testapp&request_type=1&channel=16100004&price=2000&imei=865728023092265&imsi=460031539820392&bsc_lac=100&bsc_cid=100&mobile=&iccid=null&mac=a0:93:47:4e:eb:7d&cpparm=&fmt=json&timestamp=1484306042356&isp=1001&code_id=&order_id=&ver_code=&charge_key=&charge_success=0";
		String verUrl = "setCharge?app_id=6&app_key=testapp&channel=431233&price=1000&imei=32323132&request_type=2&imsi=33112334&bsc_lac=3322&bsc_cid=33884&mobile=13800138000&iccid=299200931&mac=12maski298&cpparm=32398842&fmt=json&timestamp=1829304985&isp=1001&code_id=1&order_id=32133&ver_code=3332244";
//		String context = "1065842232:AE2000394k7q537, z529a5U03\"60b0!q71i3v1a41NR1DkW88a@(U7q$r[JOKQi=5jIfT7e6ujSf6%14201A112TE/011^\\k{-0bs000?0MIuerV8/c~z4_Xg0jse/IOztbeMJ|=";
		String context = "中文";
		context = URLEncoder.encode(context);
		String appInfLog = "appInfLog?charge_key=32efb83bed71480ea0a8713ce5fd0ee6&imsi=460002971130174&channel=16100001&logtime=2016-11-29 16:01:16&stepname=10002:&context="+context+"&timestamp=1480406476539";
		try {
			String encode = Base64.encodeBase64String(DESEncrypt.encrypt(url.getBytes()));
			System.out.println("encode:" + URLEncoder.encode(encode));
//			String plainText = URIUtil.getPathQuery(new String(DESEncrypt.decrypt(Base64.decodeBase64(encode))));
//			System.out.println("plainText:" + plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sign = DESEncrypt.getSign("6", "testapp","865728023092265", "1484306042356");
		System.out.println(URLEncoder.encode(sign));
		String sign2 = DESEncrypt.getAppInfLogSign("32efb83bed71480ea0a8713ce5fd0ee6", "460002971130174","10002:", "1480406476539");
		System.out.println(URLEncoder.encode(sign2));
		System.out.println(DESEncrypt.getSign("", "durid", "", "123456"));
	}
}