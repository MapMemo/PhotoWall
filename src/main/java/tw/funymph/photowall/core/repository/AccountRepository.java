/* AccountRepository.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core.repository;

import tw.funymph.photowall.core.Account;

/**
 * This 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface AccountRepository {

	public Account get(String identity);

	public void save(Account account) throws RepositoryException;
}
