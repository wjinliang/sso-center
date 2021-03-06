package com.topie.ssocenter.freamwork.authorization.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SimpleCrypto {
	public static String encrypt(String seed, String cleartext)
			throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes());
		return toHex(result);
	}

	public static String decrypt(String seed, String encrypted)
			throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] enc = toByte(encrypted);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}
	public static void main(String[] args) throws Exception {
		String encrypt = encrypt("zcpt@123456", "c1719caca9c586206900af54e3");
//		ShaPasswordEncoder sha = new ShaPasswordEncoder();
//		sha.setEncodeHashAsBase64(false);
//		String opsw = sha.encodePassword("lab@1234", null);
		System.out.println("id:"+encrypt);//+"\tpassword:"+opsw);
		String e = decrypt("zcpt@123456", "8DD9FB4FF1B07E203529DEE6CDBF7119");//"9342CF90A50217A15B7200AB6EE37B4B");
										  //8BF0A06251E39A7BB8C166091C5474F8
		
		//ss();
		System.out.println(e);
	}
	
	private static void ss(){
		try {
			for(int i=0;i<555;i++){
				Thread.sleep(50);
				String encrypt = encrypt("zcpt@123456", "1470726559540");
				System.out.println(encrypt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static byte[] getRawKey(byte[] seed) throws Exception {
		try {
	        
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(seed);
			kgen.init(128, sr); // 192 and 256 bits may not be available
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			return raw;
		} catch (Exception e) {
	        throw new RuntimeException(" 初始化密钥出现异常 ");
	    }
	}
	
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
	
	
}
