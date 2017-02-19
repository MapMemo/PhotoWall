/* SparkWebServiceTests.java created on 2017年2月19日.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This class tests the functionalities of {@link SparkWebService}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class SparkWebServiceTests implements SparkWebService {

	@Test
	public void testSucceed() throws Exception {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		Route alwaysSucceeded = (req, res) -> {
			return "succeeded";
		};
		Route wrapped = metaAware(alwaysSucceeded);
		Object result = wrapped.handle(request, response);
		assertSucceed(result);
		verify(response).type("application/json");
	}

	@Test
	public void testUnexpectedFail() throws Exception {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		Route alwaysSucceeded = (req, res) -> {
			throw new Exception("unexpected");
		};
		Route wrapped = metaAware(alwaysSucceeded);
		Object result = wrapped.handle(request, response);
		when(request.requestMethod()).thenReturn("GET");
		when(request.pathInfo()).thenReturn("/accounts/111");
		assertFail(result);
		verify(response).type("application/json");
		verify(response).status(500);
	}

	@Test
	public void testDomainError() throws Exception {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		Route alwaysSucceeded = (req, res) -> {
			throw new WebServiceException(404, 1003, "user not found");
		};
		Route wrapped = metaAware(alwaysSucceeded);
		Object result = wrapped.handle(request, response);
		when(request.requestMethod()).thenReturn("PUT");
		when(request.pathInfo()).thenReturn("/accounts/111");
		assertFail(result);
		verify(response).type("application/json");
		verify(response).status(404);
	}

	@Override
	public void routes() {
		// do nothing
	}

	private void assertSucceed(Object result) {
		Map<String, Object> json = new Gson().fromJson(result.toString(), Map.class);
		assertEquals(2, json.size());
		assertNotNull(json.get("meta"));
		assertNotNull(json.get("data"));
	}

	private void assertFail(Object result) {
		Map<String, Object> json = new Gson().fromJson(result.toString(), Map.class);
		assertEquals(2, json.size());
		assertNotNull(json.get("meta"));
		assertNotNull(json.get("error"));
	}
}
