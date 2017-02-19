/* AccountWebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws.auth;

import static spark.Spark.*;

import java.util.Random;

import spark.Request;
import spark.Response;
import tw.funymph.photowall.ws.SparkWebService;

/**
 * This class handles the requests to <code>/ws/accounts/*</code>.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class AccountWebService implements SparkWebService {

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
	public void routes() {
		path("/accounts", () -> {
			get("/hello", metaAware(this::hello));
		});
	}
}
