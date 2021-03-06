/* AccountRepository.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core.repository;

import tw.funymph.photowall.core.Account;

/**
 * This interface define the methods to access the {@link Account} objects
 * from the persistence storage.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface AccountRepository {

	/**
	 * Get the account instance specified by the email.
	 * 
	 * @param email the account email
	 * @return the account instance; {@code null} if not found
	 */
	public Account findByEmail(String email);

	/**
	 * Get the account instance specified by the ID.
	 * 
	 * @param id the account ID
	 * @return the account instance; {@code null} if not found 
	 */
	public Account get(String id);

	/**
	 * Get all accounts.
	 * 
	 * @return all accounts
	 */
	public Account[] getAll();

	/**
	 * Save the account instance.
	 * 
	 * @param account the account to save
	 * @throws RepositoryException if any error occurred
	 */
	public void save(Account account) throws RepositoryException;

	/**
	 * Save the account instance.
	 * 
	 * @param account the account to save
	 * @throws RepositoryException if any error occurred
	 */
	public void update(Account account) throws RepositoryException;
}
