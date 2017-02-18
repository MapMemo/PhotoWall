/* AuthenticationWebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static spark.Spark.get;
import static tw.funymph.photowall.ws.WebService.metaAware;

import java.util.Random;

import spark.Request;
import spark.Response;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class AuthenticationWebService implements WebService {

	public Object hello(Request request, Response response) throws Exception {
		if (new Random().nextBoolean()) {
			throw new Exception();
		}
		return "Hello World";
	}

	public Object register(Request request, Response response) throws Exception {
		return null;
	}

	@Override
	public void setupRoutes() {
		get("/hello", metaAware(this::hello));
	}
}
