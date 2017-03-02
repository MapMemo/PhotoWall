/* SqlAuthenticationRepositoryTests.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.sql2o;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.funymph.photowall.core.Authentication;
import tw.funymph.photowall.core.repository.RepositoryException;

/**
 * This class tests the functionalities of {@link SqlAuthenticationRepository}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class SqlAuthenticationRepositoryTests extends AbstractSqlRepositoryTestCase {

	private SqlAuthenticationRepository testee;

	@Before
	public void setUp() {
		testee = new SqlAuthenticationRepository(initSql2o());
	}

	@After
	public void tearDown() {
		dropDatabase();
	}

	@Test
	public void testSave() throws RepositoryException {
		Authentication authentication = new Authentication("aaa@bbb.com");
		String token = authentication.getToken();

		assertNull(testee.get(token));
		testee.save(authentication);

		Authentication saved = testee.get(token);
		assertNotNull(saved);
		assertEquals("aaa@bbb.com", saved.getIdentity());
		assertEquals(token, saved.getToken());
	}

	@Test
	public void testSaveDuplicated() throws Exception {
		Authentication authentication = new Authentication("aaa@bbb.com");

		assertNull(testee.get(authentication.getToken()));
		testee.save(authentication);
		try {
			testee.save(authentication);
			fail("duplicated instances should not be saved");
		}
		catch (RepositoryException e) {
			
		}
	}
}
