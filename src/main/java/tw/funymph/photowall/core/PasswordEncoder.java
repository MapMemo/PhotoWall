/* PasswordEncoder.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

/**
 * This interface defines the methods for password encoding and matching.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface PasswordEncoder {

	/**
	 * Encode the password.
	 * 
	 * @param password the password to encode
	 * @return the encoded password
	 */
	public String encode(String password);

	/**
	 * 
	 * @param rawPassword
	 * @param encodedPassword
	 * @return
	 */
	public boolean match(String rawPassword, String encodedPassword);
}
