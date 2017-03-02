/* StringUtils.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import java.security.MessageDigest;

/**
 * This interface provides utility methods for {@link String}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface StringUtils {

	static final char[] hexArray = "0123456789abcdef".toCharArray();

	/**
	 * Encode the string by SHA1 digest.
	 * 
	 * @param string the string to be encoded
	 * @return the SHA1 string
	 */
	public static String toSHA1(final String string) {
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
	public static String messageDigest(final String string, final String algorithm, final String charset) {
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
	public static String toHexString(final byte[] bytes) {
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
	public static boolean isBlank(final String string) {
		return string == null || string.trim().isEmpty();
	}

	/**
	 * Check whether the string is not blank.
	 * 
	 * @param string the string to check
	 * @return true if the string is not blank
	 */
	public static boolean notBlank(final String string) {
		return string != null && !string.trim().isEmpty();
	}

	/**
	 * Assert that the given value is not blank. If the value is blank,
	 * an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param value the value to assert
	 * @param exceptionMessage the exception message
	 * @throws IllegalArgumentException if the value is null or empty
	 * @return the value to assert
	 */
	public static String assertNotBlank(final String value, final String exceptionMessage) {
		if (notBlank(value)) {
			return value;
		}
		throw new IllegalArgumentException(exceptionMessage);
	}

	/**
	 * Join a couple of strings as a single string with separator. For example,
	 * join("|", "a", "b", "c") returns "a|b|c".
	 * 
	 * @param separator the separator between strings
	 * @param strings the strings to be joined
	 * @return the joined string
	 */
	public static String join(final String separator, final String... strings) {
		return stream(strings).reduce((s1, s2) -> format("%s%s%s", s1, separator, s2)).get();
	}

	public static boolean equalsIgnoreCase(String string1, String string2) {
		if (string1 == null && string2 == null) {
			return true;
		}
		if (string1 != null && string2 != null) {
			return string1.compareToIgnoreCase(string2) == 0;
		}
		return false;
	}
}
