/* SHA1PasswordEncoderTests.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class SHA1PasswordEncoderTests {

	@Test
	public void test() {
		PasswordEncoder testee = new SHA1PasswordEncoder();
		String encoded = testee.encode("thisispassword");
		assertNotEquals("thisispassword", encoded);
		assertEquals(45, encoded.length());

		assertTrue(testee.match("thisispassword", encoded));
		assertFalse(testee.match("thisisnotpassword", encoded));
	}
}
