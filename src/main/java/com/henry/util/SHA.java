package com.henry.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA {
	final public static byte[] salt = "sha".getBytes();

	public SHA() {}

	/**
	 * SHA 암호화!
	 * @param str
	 * @param hexMethod
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	public String getSHA(String str, String hexMethod) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(str.getBytes());

		byte byteData[] = digest.digest(salt);

		if(hexMethod.equals("1")) {
			return getSHAhex1(byteData);
		}
		return getSHAhex2(byteData);
	}

	/**
	 * SHA 암화화 ( HEX 값 )
	 * @param byteData
	 * @return String
	 */
	public String getSHAhex1(byte byteData[]) {
		//convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();

		for(int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	/**
	 * SHA 암화화 ( HEX 값 )
	 * @param byteData
	 * @return String
	 */
	public String getSHAhex2(byte byteData[]) {
		//convert the byte to hex format method 2
		StringBuffer sb = new StringBuffer();

		for(int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if(hex.length() == 1) sb.append("0");
			sb.append(hex);
		}

		return sb.toString();
	}
}
