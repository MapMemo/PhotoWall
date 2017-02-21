/* InMemoryAccountRepository.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core.repository;

import java.util.HashMap;
import java.util.Map;

import tw.funymph.photowall.core.Account;

/**
 * This class provides an implementation of {@link AccountRepository}
 * that stores the accounts only in memory (will lost on restart).
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class InMemoryAccountRepository implements AccountRepository {

	private Map<String, Account> accounts;

	/**
	 * Construct a <code>InMemoryAccountRepository</code> instance.
	 */
	public InMemoryAccountRepository() {
		accounts = new HashMap<>();
	}

	@Override
	public synchronized Account get(String identity) {
		return accounts.get(identity);
	}

	@Override
	public synchronized void save(Account account) throws RepositoryException {
		if (accounts.containsKey(account.getIdentity())) {
			throw new RepositoryException("violation of primary key constraint", null);
		}
		accounts.put(account.getIdentity(), account);
	}	
}
