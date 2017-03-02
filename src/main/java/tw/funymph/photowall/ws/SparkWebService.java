/* WebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static java.lang.String.valueOf;
import static spark.Spark.before;
import static spark.Spark.options;
import static java.lang.System.currentTimeMillis;
import static tw.funymph.photowall.utils.JsonUtils.toJson;
import static tw.funymph.photowall.utils.MapUtils.asMap;
import static tw.funymph.photowall.utils.MapUtils.notEmpty;
import static tw.funymph.photowall.utils.StringUtils.equalsIgnoreCase;
import static tw.funymph.photowall.utils.StringUtils.join;

import java.util.Map;
import java.util.function.Predicate;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This interface provides the default methods for most common utilities.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface SparkWebService extends HttpMethods, HttpStatusCodes, HttpHeaders, HttpContentTypes {

	public static final String[] DefaultAllowMethods = { Get, Delete, Put, Post, Options };
	public static final String[] DefaultAllowHeaders = { Authorization, AuthToken, Accept, AcceptCharset, AcceptEncoding, AcceptLanguage, AcceptDatetime, ContentType, ContentDisposition };

	public static void enableCORS() {
		enableCORS("*", DefaultAllowMethods, DefaultAllowHeaders);
	}

	public static void enableCORS(final String origin, final String[] methods, final String[] headers) {
		options("/*", (request, response) -> {
			String accessControlRequestHeaders = request.headers(AccessControlRequestHeaders);
			if (accessControlRequestHeaders != null) {
				response.header(AccessControlAllowHeaders, accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers(AccessControlRequestMethod);
			if (accessControlRequestMethod != null) {
				response.header(AccessControlAllowMethods, accessControlRequestMethod);
			}
			return "";
		});

		before((request, response) -> {
			response.header(AccessControlAllowOrigin, origin);
			response.header(AccessControlRequestMethod, join(", ", methods));
			response.header(AccessControlAllowHeaders, join(", ", headers));
		});
	}

	/**
	 * Intercept the route with the additional function that wraps the result
	 * or exception as the well-formatted response.
	 * 
	 * @param route the route to intercept
	 * @return the intercepted route
	 */
	public default Route metaAware(Route route) {
		return (request, response) -> {
			long timestamp = currentTimeMillis();
			response.header(Timestamp, valueOf(timestamp));
			try {
				Object result = route.handle(request, response);
				response.header(Elapsed, valueOf((currentTimeMillis() - timestamp)));
				if (result != null) {
					response.type(ApplicationJson);
					return toJson(asMap("data", result));
				}
				return equalsIgnoreCase(request.requestMethod(), Get)? null : "";
			}
			catch (WebServiceException e) {
				return wrapException(response, timestamp, e);
			}
			catch (Throwable e) {
				return wrapException(response, timestamp, new WebServiceException(e));
			}
		};
	}

	public static Object wrapException(Response response, long timestamp, WebServiceException e) {
		response.type(ApplicationJson);
		response.status(e.getStatusCode());
		response.header(Elapsed, valueOf((currentTimeMillis() - timestamp)));
		Map<String, Object> error = asMap("code", e.getErrorCode());
		error.put("message", e.getMessage());
		if (notEmpty(e.getInfo())) {
			error.put("info", e.getInfo());
		}
		return toJson(asMap("error", error));
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
