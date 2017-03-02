/* HttpHeadersTests.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static org.junit.Assert.*;
import static tw.funymph.photowall.ws.HttpHeaders.basicAuthorization;
import static tw.funymph.photowall.ws.HttpHeaders.contentDispositionFilename;

import org.junit.Test;

/**
 * This class tests the functionalities of {@link HttpHeaders}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class HttpHeadersTests {

	@Test
	public void testBasicAuthorization() {
		String[] credentials = basicAuthorization("Basic YWFhQGJiYi5jb206Y2NjYw==");
		assertEquals(2, credentials.length);
		assertEquals("aaa@bbb.com", credentials[0]);
		assertEquals("cccc", credentials[1]);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidBasicAuthorization() {
		basicAuthorization("sfsfiijskn");
	}

	@Test
	public void testContentDispositionFilename() {
		assertEquals("fname.ext", contentDispositionFilename("attachment; filename=\"fname.ext\""));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidContentDispositionFilename() {
		contentDispositionFilename("attachment");
	}
}
