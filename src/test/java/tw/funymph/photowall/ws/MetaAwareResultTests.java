/* MetaAwareResultTests.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static org.junit.Assert.*;
import static tw.funymph.photowall.utils.MapUtils.asMap;

import java.util.Map;

import org.junit.Test;

/**
 * This class tests the functionalities of {@link MetaAwareResult}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class MetaAwareResultTests {

	@Test
	public void testSucceed() {
		MetaAwareResult testee = new MetaAwareResult();
		String data = "Hello World!";
		Map<String, Object> result = testee.succeed(data);
		assertEquals(2, result.size());
		assertNotNull(result.get("meta"));
		assertEquals(data, result.get("data"));

		testee = new MetaAwareResult();
		result = testee.succeed(null);
		assertEquals(1, result.size());
		Map<String, Object> meta = (Map<String, Object>)result.get("meta");
		assertNotNull(meta);
		assertEquals(testee.timestamp, meta.get("timestamp"));
		assertEquals(testee.elapsed, meta.get("elapsed"));
	}

	@Test
	public void testFail() {
		MetaAwareResult testee = new MetaAwareResult();
		WebServiceException e = new WebServiceException(404, 1101, "the account does not exist");
		Map<String, Object> result = testee.fail("/accounts/123", "GET", e);
		assertEquals(2, result.size());
		assertNotNull(result.get("meta"));
		assertNotNull(result.get("error"));
		Map<String, Object> meta = (Map<String, Object>)result.get("meta");
		assertNotNull(meta);
		assertEquals(testee.timestamp, meta.get("timestamp"));
		assertEquals(testee.elapsed, meta.get("elapsed"));

		testee = new MetaAwareResult(true);
		e = new WebServiceException(409, 1102, "the account does not exist", asMap("identity", "abc@xxx.com"));
		result = testee.fail("/accounts", "POST", e);
		Map<String, Object> error = (Map<String, Object>)result.get("error");
		assertEquals("/accounts", error.get("path"));
		assertEquals("POST", error.get("method"));
		assertEquals(409, error.get("status"));
		assertEquals(1102, error.get("code"));

		Object[] traces = (Object[])error.get("traces");
		assertEquals(e.getStackTrace().length, traces.length);
		assertEquals("at tw.funymph.photowall.ws.MetaAwareResultTests.testFail(MetaAwareResultTests.java.58)", traces[0]);
	}
}
