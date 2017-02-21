/* AccountManager.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

/**
 * This interface defines the contracts to the account manager.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface AccountManager {

	/**
	 * Register a new account with the identity (could be email or some
	 * information to identify the account), nickname, and (hashed) password.
	 * 
	 * @param identity the identity
	 * @param nickname the nickname
	 * @param password the password
	 * @return the new created account
	 * @throws AccountManagerException if the identity has been used by other
	 * @throws IllegalArgumentException if one of the arguments is null or empty
	 */
	public Account register(String identity, String nickname, String password) throws AccountManagerException;
}
