/* InMemoryAccountRepositoryTests.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tw.funymph.photowall.core.Account;

/**
 * This class tests the functionalities of {@link InMemoryAccountRepository}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class InMemoryAccountRepositoryTests {

	private InMemoryAccountRepository testee;

	@Before
	public void setup() {
		testee = new InMemoryAccountRepository();
	}

	@Test
	public void testSave() throws RepositoryException {
		Account account = new Account("aaa@bbb.com", "AAA", "thisispassword");
		testee.save(account);
		assertEquals(account, testee.get("aaa@bbb.com"));

		try {
			testee.save(account);
			fail("a repository exception should be thrown");
		}
		catch (RepositoryException e) {
			
		}
	}
}
