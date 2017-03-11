/* DefaultAccountManager.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import static tw.funymph.photowall.utils.StringUtils.assertNotBlank;

import tw.funymph.photowall.core.repository.AccountRepository;
import tw.funymph.photowall.core.repository.AuthenticationRepository;
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
	private AuthenticationRepository authenticationRepository;

	/**
	 * Construct a <code>DefaultAccountManager</code> instance with the
	 * account repository.
	 * 
	 * @param accountRepository the account repository
	 */
	public DefaultAccountManager(AccountRepository accountRepository, AuthenticationRepository authenticationRepository) {
		this.accountRepository = accountRepository;
		this.authenticationRepository = authenticationRepository;
		passwordEncoder = new SHA1PasswordEncoder();
	}

	@Override
	public Account register(String identity, String nickname, String password) throws AccountManagerException {
		assertNotBlank(identity, "the identity can not be blank");
		assertNotBlank(nickname, "the nickname can not be blank");
		assertNotBlank(password, "the password can not be blank");
		Account account = accountRepository.findByEmail(identity);
		if (account != null) {
			throw new AccountManagerException("the identity has already been used");
		}
		account = new Account(identity, nickname, passwordEncoder.encode(password));
		try {
			accountRepository.save(account);
			return account;
		}
		catch (RepositoryException e) {
			throw new AccountManagerException("unable to save account", e);
		}
	}

	@Override
	public Authentication login(String identity, String password) throws AccountManagerException {
		assertNotBlank(identity, "the identity can not be blank");
		assertNotBlank(password, "the password can not be blank");
		Account account = accountRepository.findByEmail(identity);
		if (account == null) {
			throw new AccountManagerException("the identity does not exist");
		}
		if (!passwordEncoder.match(password, account.getPassword())) {
			throw new AccountManagerException("the password does not match");
		}
		Authentication authentication = new Authentication(identity);
		try {
			authenticationRepository.save(authentication);
			return authentication;
		}
		catch (RepositoryException e) {
			throw new AccountManagerException("unable to save the authentication", e);
		}
	}

	@Override
	public void logout(String token) throws AccountManagerException {
		try {
			authenticationRepository.delete(token);
		} catch (RepositoryException e) {
			throw new AccountManagerException("unable to remove the authentication", e);
		}
	}

	@Override
	public Account checkAccount(String token) {
		Authentication authentication = authenticationRepository.get(token);
		if (authentication != null) {
			return accountRepository.findByEmail(authentication.getIdentity());
		}
		return null;
	}

	@Override
	public Account getAccount(String id) {
		return accountRepository.get(id);
	}

	@Override
	public Account[] getAll() {
		return accountRepository.getAll();
	}
}
