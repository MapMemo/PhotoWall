/* WebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static tw.funymph.photowall.utils.JsonUtils.toJson;

import spark.Route;

/**
 * This interface provides the default methods for most common utilities.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface SparkWebService extends HttpStatusCodes, ContentTypes {

	/**
	 * Intercept the route with the additional function that wraps the result
	 * or exception as the well-formatted response.
	 * 
	 * @param route the route to intercept
	 * @return the intercepted route
	 */
	public default Route metaAware(Route route) {
		return jsonize((request, response) -> {
			MetaAwareResult result = new MetaAwareResult();
			try {
				Object data = route.handle(request, response);
				return result.succeed(data);
			}
			catch (WebServiceException e) {
				response.status(e.getStatusCode());
				return result.fail(request.pathInfo(), request.requestMethod(), e);
			}
			catch (Throwable e) {
				response.status(InternalServerError);
				return result.fail(request.pathInfo(), request.requestMethod(), new WebServiceException(e));
			}
		});
	}

	public default Route jsonize(Route route) {
		return (request, response) -> {
			response.type(ApplicationJson);
			return toJson(route.handle(request, response));
		};
	}

	/**
	 * Setup the routes for the instance.
	 */
	public void routes();
}
