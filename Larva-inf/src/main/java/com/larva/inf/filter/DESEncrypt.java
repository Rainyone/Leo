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
	private static String SHA_KEY = "charge";

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
		String url = "setCharge?app_id=613c6412db374a519cf98410085489c8&app_key=4c4a824602c948a2bc87b1382dd3a5dc&channel=1&price=1000&imei=860917020646570&request_type=1&imsi=460020586922644&bsc_lac=3322&bsc_cid=33884&mobile=1390023133&iccid=299200931&mac=12maski298&cpparm=32398842&fmt=json&timestamp=1829304985&isp=1001";
		String verUrl = "setCharge?app_id=6&app_key=testapp&channel=431233&price=1000&imei=32323132&request_type=2&imsi=33112334&bsc_lac=3322&bsc_cid=33884&mobile=13800138000&iccid=299200931&mac=12maski298&cpparm=32398842&fmt=json&timestamp=1829304985&isp=1001&code_id=1&order_id=32133&ver_code=3332244";
		String appInfLog = "appInfLog?charge_key=00004c4d843b4aa493323dde1e1c3892&imsi=4444&channel=dsdfsd&logtime=2015-06-23 21:22:32&stepname=sims&context=dfalskjdfkl33&timestamp=241545225";
		try {
			String encode = Base64.encodeBase64String(DESEncrypt.encrypt(appInfLog.getBytes()));
			System.out.println("encode:" + URLEncoder.encode(encode));
//			String plainText = URIUtil.getPathQuery(new String(DESEncrypt.decrypt(Base64.decodeBase64(encode))));
//			System.out.println("plainText:" + plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sign = DESEncrypt.getSign("613c6412db374a519cf98410085489c8", "4c4a824602c948a2bc87b1382dd3a5dc","860917020646570", "1829304985");
		System.out.println(URLEncoder.encode(sign));
		String sign2 = DESEncrypt.getAppInfLogSign("00004c4d843b4aa493323dde1e1c3892", "4444","sims", "241545225");
		System.out.println(URLEncoder.encode(sign2));
	}
}