/* WebServiceExceptionTests.java created on Feb 19, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static org.junit.Assert.*;
import static tw.funymph.photowall.utils.MapUtils.asMap;

import org.junit.Test;

/**
 * This class tests the functionalities of {@link WebServiceException}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class WebServiceExceptionTests {

	@Test
	public void test() {
		Exception unexpected = new Exception("something happened");
		WebServiceException testee = new WebServiceException(unexpected);

		assertEquals(-1, testee.getErrorCode());
		assertEquals(500, testee.getStatusCode());

		Exception domainError = new Exception("user not found");
		testee = new WebServiceException(404, 1103, domainError);
		assertEquals(1103, testee.getErrorCode());
		assertEquals(404, testee.getStatusCode());
		assertEquals(domainError, testee.getCause());

		testee = new WebServiceException(406, 1106, "no user ID in the request", asMap("query parameter", "userId"));
		assertEquals(1106, testee.getErrorCode());
		assertEquals(406, testee.getStatusCode());
		assertFalse(testee.getInfo().isEmpty());
	}
}
