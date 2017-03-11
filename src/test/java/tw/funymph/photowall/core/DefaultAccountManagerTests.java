/* DefaultAccountManagerTests.java created on Feb 22, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import tw.funymph.photowall.core.repository.AccountRepository;
import tw.funymph.photowall.core.repository.RepositoryException;
import tw.funymph.photowall.utils.StringUtils;

/**
 * This class tests the functionalities of {@link DefaultAccountManager}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class DefaultAccountManagerTests {

	private AccountRepository repository;
	private DefaultAccountManager testee;

	@Before
	public void setup() {
		repository = mock(AccountRepository.class);
		testee = new DefaultAccountManager(repository, null);
	}

	@Test
	public void testRegisterArguments() throws Exception {
		assertIllegalArguments(null, "aaa", "thisispassword");
		assertIllegalArguments("aaa@bbb.com", null, "thisispassword");
		assertIllegalArguments("aaa@bbb.com", "aaa", null);
		assertIllegalArguments("", "aaa", "thisispassword");
		assertIllegalArguments("aaa@bbb.com", "", "thisispassword");
		assertIllegalArguments("aaa@bbb.com", "aaa", "");
	}

	@Test
	public void testRegisterButIdentityAlreadyUsed() throws Exception {
		Account account = new Account("aaa@bbb.com", "aaa", "thisispassword");
		when(repository.findByEmail("aaa@bbb.com")).thenReturn(account);
		try {
			testee.register("aaa@bbb.com", "aaa", "thisispassword");
			fail("an account manager exception should be thrown");
		}
		catch (AccountManagerException e) {
			
		}
		verify(repository, never()).save(any());
	}

	@Test
	public void testRegister() throws Exception {
		Account account = testee.register("aaa@bbb.com", "aaa", "thisispassword");
		assertEquals("aaa@bbb.com", account.getEmail());
		assertEquals("aaa", account.getNickname());

		// the password should be encoded
		assertTrue(StringUtils.notBlank(account.getPassword()));
		assertNotEquals("thisispassword", account.getPassword());
	}

	@Test
	public void testRegisterButRepositoryFailed() throws Exception {
		doThrow(new RepositoryException("violation of primary key constraint", null)).when(repository).save(any(Account.class));
		try {
			testee.register("aaa@bbb.com", "aaa", "thisispassword");
			fail("an account manager exception should be thrown");
		}
		catch (AccountManagerException e) {
			assertEquals(RepositoryException.class, e.getCause().getClass());
		}
	}

	private void assertIllegalArguments(String identity, String nickname, String password) throws Exception {
		try {
			testee.register(identity, nickname, password);
			fail("an illegal argument exception should be thrown");
		}
		catch (IllegalArgumentException e) {
			
		}
	}
}
