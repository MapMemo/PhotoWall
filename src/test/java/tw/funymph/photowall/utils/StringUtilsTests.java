/* StringUtils.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import static org.junit.Assert.*;
import static tw.funymph.photowall.utils.StringUtils.*;

import org.junit.Test;

/**
 * This class tests the functionalities of {@link StringUtils}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class StringUtilsTests {

	@Test
	public void testToSHA1() {
		assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", toSHA1(""));
		assertEquals("3fb6dedf506696b087198c381da92cb04f0fb71e", toSHA1("asdfajlskflsajf"));
	}

	@Test
	public void testMessageDigest() {
		assertNull(messageDigest("", "", ""));
	}

	@Test
	public void testJoinIntStringString() {
		
	}

	@Test
	public void testIsBlank() {
		assertTrue(isBlank(null));
		assertTrue(isBlank(""));
		assertFalse(isBlank("a"));
	}

	@Test
	public void testNotBlank() {
		assertTrue(notBlank("aaa"));
		assertFalse(notBlank(null));
		assertFalse(notBlank(""));
	}

	@Test
	public void testAssertNotBlankWithBlankStringAndMessage() {
		boolean exceptionThrown = false;
		try {
			assertNotBlank("", "the value can not be blank");
			fail("an exception should be thrown");
		}
		catch (IllegalArgumentException e) {
			exceptionThrown = true;
			assertEquals("the value can not be blank", e.getMessage());
		}
		assertTrue(exceptionThrown);
	}

	@Test
	public void testAssertNotBlank() {
		try {
			assertNotBlank("a", "the parameter should not be blank");
		} catch (Throwable e) {
			fail("it should not come to here!");
		}
	}

	@Test
	public void testJoin() {
		assertEquals("1,2,3,4", StringUtils.join(",", "1", "2", "3", "4"));
	}

	@Test
	public void testEqualsIgnoreCase() {
		assertTrue(equalsIgnoreCase(null, null));
		assertFalse(equalsIgnoreCase(null, "aa"));
		assertFalse(equalsIgnoreCase("bb", null));
		assertFalse(equalsIgnoreCase("bb", "aa"));
		assertTrue(equalsIgnoreCase("AAA", "aaa"));
	}

	@Test
	public void testGetExtension() {
		assertNull(getExtension("abc"));
		assertEquals("png", getExtension("abc.png"));
		assertEquals("png", getExtension("abc.123.png"));
		assertEquals("png", getExtension(".abc.png"));
	}
}
