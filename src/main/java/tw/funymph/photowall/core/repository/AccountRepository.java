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
	 * Get the account instance specified by the identity.
	 * 
	 * @param identity the account identity
	 * @return the account instance; {@code null} if not found
	 */
	public Account get(String identity);

	/**
	 * Save the account instance.
	 * 
	 * @param account the account to save
	 * @throws RepositoryException if any error occurred
	 */
	public void save(Account account) throws RepositoryException;
}
