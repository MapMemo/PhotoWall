/* SHA1PasswordEncoder.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import static java.util.UUID.randomUUID;
import static tw.funymph.photowall.utils.StringUtils.toSHA1;

import java.util.Objects;

/**
 * This class provides the SHA-1 implementation of {@link PasswordEncoder}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class SHA1PasswordEncoder implements PasswordEncoder {

	private static final int DefaultSaltLength = 5;

	private int saltLength;

	/**
	 * Construct a <code>SHA1PasswordEncoder</code> instance with
	 * the default random salt length.
	 */
	public SHA1PasswordEncoder() {
		this(DefaultSaltLength);
	}

	/**
	 * Construct a <code>SHA1PasswordEncoder</code> instance with the
	 * given random salt length.
	 * 
	 * @param salt the random salt length
	 */
	public SHA1PasswordEncoder(int salt) {
		saltLength = salt;
	}

	@Override
	public String encode(String password) {
		String salt = randomUUID().toString().substring(0, saltLength);
		return String.format("%s%s", salt, hash(salt, password));
	}

	@Override
	public boolean match(String rawPassword, String encodedPassword) {
		String salt = encodedPassword.substring(0, saltLength);
		String hash = encodedPassword.substring(saltLength);
		return Objects.equals(hash, hash(salt, rawPassword));
	}

	String hash(String salt, String password) {
		String saltedPassword = String.format("%s%s", salt, password);
		return toSHA1(saltedPassword);
	}
}
