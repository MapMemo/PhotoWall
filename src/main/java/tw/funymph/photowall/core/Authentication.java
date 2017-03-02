/* Authentication.java created on Mar 1, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import static java.util.UUID.randomUUID;

/**
 * This class encapsulates the login information.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class Authentication {

	private String identity;
	private String token;

	/**
	 * Construct a <code>Authentication</code> instance with the identity
	 * and the token.
	 * 
	 * @param identity the identity
	 * @param token the issued access token
	 */
	public Authentication(String identity, String token) {
		this.identity = identity;
		this.token = token;
	}

	/**
	 * Construct a <code>Authentication</code> instance with the identity
	 * and random UUID access token.
	 * 
	 * @param identity the identity
	 */
	public Authentication(String identity) {
		this(identity, randomUUID().toString());
	}

	/**
	 * Get the identity.
	 * 
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * Get the access token.
	 * 
	 * @return the access token
	 */
	public String getToken() {
		return token;
	}
}
