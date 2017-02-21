/* Account.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

/**
 * This class represents an account in the system.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class Account {

	private String identity;
	private String nickname;
	private String password;

	/**
	 * Construct a <code>Account</code> instance with the identity, nickname,
	 * and hashed password.
	 * 
	 * @param identity the account identity
	 * @param nickname the nickname
	 * @param password the hashed password 
	 */
	public Account(String identity, String nickname, String password) {
		setIdentity(identity);
		setNickname(nickname);
		setPassword(password);
	}

	/**
	 * Get the account's identity.
	 * 
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * Get the account owner's nickname.
	 * 
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Get the hashed password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the new nickname.
	 * 
	 * @param value the new nickname
	 */
	public void setNickname(String value) {
		nickname = value;
	}

	/**
	 * Set the new hashed password.
	 * 
	 * @param value the new hahsed password
	 */
	public void setPassword(String value) {
		password = value;
	}

	/**
	 * Set the account's identity (this would be used by reflection tool).
	 * 
	 * @param value the identity
	 */
	private void setIdentity(String value) {
		identity = value;
	}
}
