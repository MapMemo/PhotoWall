/* WebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import com.google.gson.Gson;

import spark.Route;

/**
 * This interface provides the default methods for most common utilities.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface SparkWebService {

	/**
	 * Intercept the route with the additional function that wraps the result
	 * or exception as the well-formatted response.
	 * 
	 * @param route the route to intercept
	 * @return the intercepted route
	 */
	public default Route metaAware(Route route) {
		return (request, response) -> {
			MetaAwareResult result = new MetaAwareResult();
			response.type("application/json");
			try {
				Object data = route.handle(request, response);
				return new Gson().toJson(result.succeed(data));
			}
			catch (WebServiceException e) {
				response.status(e.getStatusCode());
				return new Gson().toJson(result.fail(request.pathInfo(), request.requestMethod(), e));
			}
			catch (Exception e) {
				response.status(500);
				return new Gson().toJson(result.fail(request.pathInfo(), request.requestMethod(), new WebServiceException(e)));
			}
		};
	}

	/**
	 * Setup the routes for the instance.
	 */
	public void routes();
}
