/* Account.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import static java.util.UUID.randomUUID;

/**
 * This class represents an account in the system.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class Account {

	private String id;
	private String email;
	private String nickname;
	private String password;

	/**
	 * Construct a <code>Account</code> instance with the email,
	 * nickname, and hashed password.
	 * 
	 * @param email the account identity
	 * @param nickname the nickname
	 * @param password the hashed password 
	 */
	public Account(String email, String nickname, String password) {
		this.id = randomUUID().toString();
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	/**
	 * Get the system generated ID.
	 * 
	 * @return the system generated ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the account's email.
	 * 
	 * @return the identity
	 */
	public String getEmail() {
		return email;
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
}
