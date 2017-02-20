/* WebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static tw.funymph.photowall.utils.JsonUtils.toJson;

import java.util.function.Predicate;

import spark.Request;
import spark.Route;

/**
 * This interface provides the default methods for most common utilities.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface SparkWebService extends HttpStatusCodes, HttpHeaders, HttpContentTypes {

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

	/**
	 * Convert the resulting object to JSON format as the response.
	 * 
	 * @param route the route to generate the result
	 * @return the route that format the object as JSON
	 */
	public default Route jsonize(Route route) {
		return (request, response) -> {
			response.type(ApplicationJson);
			return toJson(route.handle(request, response));
		};
	}

	/**
	 * Check whether the request contains the required information for authentication.
	 * If the authentication failed, an {@link WebServiceException} with the status
	 * code {@link HttpStatusCodes#Unauthorized} is thrown. The predicate can be
	 * chained with {@link Predicate#or(Predicate)} or {@link Predicate#and(Predicate)}
	 * that provides complicated authentication evaluation.
	 * 
	 * @param route the route to authenticate
	 * @param predicate the authentication evaluator
	 * @return the route that evaluate the authentication before invoke the wrapped route
	 */
	public default Route authenticate(Route route, Predicate<Request> predicate) {
		return (request, response) -> {
			if (predicate.negate().test(request)) {
				throw new WebServiceException(Unauthorized, -1, "the request needs authentication");
			}
			return route.handle(request, response);
		};
	}

	/**
	 * Check whether the request contains the required information for authorization.
	 * If the authorization failed, an {@link WebServiceException} with the status
	 * code {@link HttpStatusCodes#Forbidden} is thrown. The predicate can be chained
	 * with {@link Predicate#or(Predicate)} or {@link Predicate#and(Predicate)}
	 * that provides complicated authorization evaluation.
	 * 
	 * @param route the route to authorize
	 * @param predicate the authorization evaluator
	 * @return the route that evaluate the authorization before invoke the wrapped route
	 */
	public default Route authorize(Route route, Predicate<Request> predicate) {
		return (request, response) -> {
			if (predicate.negate().test(request)) {
				throw new WebServiceException(Forbidden, -1, "the request needs authorization");
			}
			return route.handle(request, response);
		};
	}

	/**
	 * Setup the routes for the instance.
	 */
	public void routes();
}
