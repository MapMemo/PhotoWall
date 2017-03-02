/* SqlAccountRepositoryTests.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.funymph.photowall.core.Account;
import tw.funymph.photowall.core.repository.RepositoryException;

/**
 * This class tests the functionalities of {@code SqlAccountRepository}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class SqlAccountRepositoryTests extends AbstractSqlRepositoryTestCase {

	private SqlAccountRepository testee;

	@Before
	public void setUp() {
		testee = new SqlAccountRepository(initSql2o());
	}

	@After
	public void tearDown() {
		dropDatabase();
	}

	@Test
	public void testSave() throws Exception {
		assertNull(testee.get("aaa@bbb.com"));
		Account account = new Account("aaa@bbb.com", "AAA", "cccc");
		testee.save(account);

		Account saved = testee.get("aaa@bbb.com");
		assertEquals("AAA", saved.getNickname());
		assertEquals("cccc", saved.getPassword());
	}

	@Test
	public void testSaveDuplicated() throws Exception {
		assertNull(testee.get("aaa@bbb.com"));
		Account account = new Account("aaa@bbb.com", "AAA", "cccc");
		testee.save(account);
		try {
			testee.save(account);
			fail("duplicated instances should not be saved");
		}
		catch (RepositoryException e) {
			
		}
	}
}
