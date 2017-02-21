/* StringUtils.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import java.security.MessageDigest;

/**
 * @author spirit
 * @version 
 * @since 
 */
public interface StringUtils {

	static final char[] hexArray = "0123456789abcdef".toCharArray();

	/**
	 * Encode the string by SHA1 digest.
	 * 
	 * @param string the string to be encoded
	 * @return the SHA1 string
	 */
	public static String toSHA1(String string) {
		return messageDigest(string, "SHA-1", "iso-8859-1");
    }

	/**
	 * Encode the string by giving digest algorithm and character set.
	 * 
	 * @param string the string to be encoded
	 * @param algorithm the message digest algorithm
	 * @param charset the character set of the string
	 * @return the encoded string
	 */
	public static String messageDigest(String string, String algorithm, String charset) {
		try {
	        MessageDigest md = MessageDigest.getInstance(algorithm);
	        md.update(string.getBytes(charset), 0, string.length());
	        byte[] sha1hash = md.digest();
	        return toHexString(sha1hash);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * Convert the unsigned byte array to a hexadecimal string.
	 * 
	 * @param bytes the unsigned byte array to be converted
	 * @return the hexadecimal string
	 */
	public static String toHexString(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		int value;
		for (int index = 0; index < bytes.length; index++) {
			value = bytes[index] & 0xFF;
			hexChars[index * 2] = hexArray[value >> 4];
			hexChars[index * 2 + 1] = hexArray[value & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * Check whether the string is blank.
	 * 
	 * @param string the string to check
	 * @return true if the string is null or blank
	 */
	public static boolean isBlank(String string) {
		return string == null || string.trim().isEmpty();
	}

	/**
	 * Check whether the string is not blank.
	 * 
	 * @param string the string to check
	 * @return true if the string is not blank
	 */
	public static boolean notBlank(String string) {
		return string != null && !string.trim().isEmpty();
	}

	/**
	 * Assert that the given value is not blank. If the value is blank,
	 * an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param value the value to assert
	 * @param exceptionMessage the exception message
	 * @throws IllegalArgumentException if the value is null or empty
	 */
	public static void assertNotBlank(String value, String exceptionMessage) {
		if (notBlank(value)) {
			return;
		}
		throw new IllegalArgumentException(exceptionMessage);
	}
}
