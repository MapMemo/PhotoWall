/* DefaultAccountManager.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import static tw.funymph.photowall.utils.StringUtils.assertNotBlank;

import tw.funymph.photowall.core.repository.AccountRepository;
import tw.funymph.photowall.core.repository.RepositoryException;

/**
 * This class provides the default implementation of {@link AccountManager}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class DefaultAccountManager implements AccountManager {

	private PasswordEncoder passwordEncoder;
	private AccountRepository accountRepository;

	/**
	 * Construct a <code>DefaultAccountManager</code> instance with the
	 * account repository.
	 * 
	 * @param repository the account repository
	 */
	public DefaultAccountManager(AccountRepository repository) {
		accountRepository = repository;
		passwordEncoder = new SHA1PasswordEncoder();
	}

	@Override
	public Account register(String identity, String nickname, String password) throws AccountManagerException {
		assertNotBlank(identity, "the identity can not be blank");
		assertNotBlank(nickname, "the nickname can not be blank");
		assertNotBlank(password, "the password can not be blank");
		Account account = accountRepository.get(identity);
		if (account != null) {
			throw new AccountManagerException("the identity has already been used");
		}
		account = new Account(identity, nickname, passwordEncoder.encode(password));
		try {
			accountRepository.save(account);
		}
		catch (RepositoryException e) {
			throw new AccountManagerException("unable to save account", e);
		}
		return account;
	}
}
