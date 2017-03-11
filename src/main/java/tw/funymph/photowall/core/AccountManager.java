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

	/**
	 * Login as the given account identity with the specified password.
	 * 
	 * @param identity the account identity
	 * @param password the (hashed) password
	 * @return the authentication with the identity and the access token
	 * @throws AccountManagerException if the identity and password does not match
	 */
	public Authentication login(String identity, String password) throws AccountManagerException;

	/**
	 * Use the token the find the authentication for logout.
	 * 
	 * @param token the authentication token
	 * @throws AccountManagerException if any error occurred
	 */
	public void logout(String token) throws AccountManagerException;

	/**
	 * Check whether the token is valid or not.
	 * 
	 * @param token the token to check
	 * @return the actual account if the token is valid; {@code null} if invalid
	 */
	public Account checkAccount(String token);

	/**
	 * Get account specified by the ID.
	 * 
	 * @param id the account ID
	 * @return the specified account; {@code null} if the account does not exist
	 */
	public Account getAccount(String id);

	/**
	 * Get all accounts.
	 * 
	 * @return all accounts
	 */
	public Account[] getAll();
}
