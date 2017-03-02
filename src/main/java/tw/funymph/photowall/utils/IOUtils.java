/* IOUtils.java created on Feb 23, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import static java.security.MessageDigest.getInstance;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * This class provides IO related utility methods.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface IOUtils {

	public static final int EOF = -1;

	static final int DefaultBufferSize = 4096;
	static final String DigestTypeMD5 = "MD5";
	static final char[] HexArray = "0123456789abcdef".toCharArray();

	/**
	 * Close the given {@link Closeable} resource (e.g., {@link java.io.InputStream InputStream},
	 * {@link java.io.OutputStream OutputStream}, {@link java.io.Reader Reader}, or
	 * {@link java.io.Writer Writer}) without the exception handling. Note that this
	 * method should ONLY be used in final cause, this method is not the way to avoid
	 * writing exception handling.
	 *
	 * @param closeable the resource to be closed.
	 */
	public static void closeSilently(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Copy the content from the input stream into the output stream. Note that the method does
	 * <em>NOT</em> close the given input stream and output stream, the caller should close the
	 * given streams.
	 *
	 * @param input the content source
	 * @param output the copy target
	 * @return the copied bytes; {@value #EOF} means error occurs
	 */
	public static long copy(InputStream input, OutputStream output) {
		return copy(input, output, optimalSizedBuffer());
	}

	/**
	 * Copy the content from the input stream into the output stream. Note that the method does
	 * <em>NOT</em> close the given input stream and output stream, the caller should close the
	 * given streams.
	 *
	 * @param input the content source
	 * @param output the copy target
	 * @param buffer the buffer to be used during copy
	 * @return the copied bytes; {@value #EOF} means error occurs
	 */
	public static long copy(InputStream input, OutputStream output, byte[] buffer) {
		if (input == null || output == null || buffer == null) {
			return EOF;
		}

		int read = 0;
		long copied = 0;
		try {
			while ((read = input.read(buffer)) != EOF) {
				output.write(buffer, 0, read);
				copied += read;
			}
			output.flush();
		}
		catch (IOException e) {
			copied = EOF;
		}
		return copied;
	}

	/**
	 * Create an optimal sized buffer, usually the size is 4 KB for most platforms.
	 * 
	 * @return the optimal sized buffer
	 */
	public static byte[] optimalSizedBuffer() {
		return new byte[DefaultBufferSize];
	}

	/**
	 * Convert the given string as an input stream that contains the string.
	 *
	 * @param input the string to be converted
	 * @return the input stream that contains the string
	 */
	public static InputStream toInputStream(String input) {
		return toInputStream(input, Charset.forName("UTF-8"));
	}

	/**
	 * Convert the given string as an input stream that contains the string using
	 * the specified charset.
	 *
	 * @param input the string to be converted
	 * @param charset the charset used in the conversion
	 * @return the input stream that contains the string
	 */
	public static InputStream toInputStream(String input, Charset charset) {
		return (input != null && charset != null) ? new ByteArrayInputStream(input.getBytes(charset)) : null;
	}

	/**
	 * Retrieve the content from the input stream as a string.
	 *
	 * @param input the input stream to retrieve
	 * @return the content from the input stream
	 */
	public static String toString(InputStream input) {
		return toString(input, Charset.forName("UTF-8"));
	}

	/**
	 * Retrieve the content from the input stream as a string using the specified
	 * charset. Note that the method does <em>NOT</em> close the given input stream,
	 * and the caller should close the input stream.
	 *
	 * @param input the input stream to retrieve
	 * @param charset the chartset used in the conversion
	 * @return the content from the input stream
	 */
	public static String toString(InputStream input, Charset charset) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		String result = new String(output.toByteArray(), charset);
		closeSilently(output);
		return result;
	}

	/**
	 * Encode the input stream by MD5 digest. Note that the method does <em>NOT</em>
	 * close the given input stream, and the caller should close the input stream.
	 *
	 * @param input the input stream to be encoded
	 * @return the MD5 string
	 */
	public static String toMD5(InputStream input) {
		if (input == null) {
			return null;
		}
		String md5 = null;
		int read = 0;
		byte[] buffer = new byte[DefaultBufferSize];
		try {
			MessageDigest messageDigest = getInstance(DigestTypeMD5);
			while ((read = input.read(buffer)) > 0) {
				messageDigest.update(buffer, 0, read);
			}
			byte[] digest = messageDigest.digest();
			char[] hexChars = new char[digest.length * 2];
			int value;
			for (int index = 0; index < digest.length; index++) {
				value = digest[index] & 0xFF;
				hexChars[index * 2] = HexArray[value >> 4];
				hexChars[index * 2 + 1] = HexArray[value & 0x0F];
			}
			md5 = new String(hexChars);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return md5;
	}

	/**
	 * Encode the input stream by MD5 digest.
	 * 
	 * @param file the file to encode
	 * @return the MD5 string
	 */
	public static String toMD5(File file) {
		if (file == null) {
			return null;
		}
		String md5 = null;
		FileInputStream stream = null;
		BufferedInputStream buffer = null;
		try {
			stream = new FileInputStream(file);
			buffer = new BufferedInputStream(stream);
			md5 = toMD5(buffer);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			closeSilently(stream);
			closeSilently(buffer);
		}
		return md5;
	}
}
