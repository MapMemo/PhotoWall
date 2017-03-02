/* WebServiceException.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static tw.funymph.photowall.ws.HttpStatusCodes.InternalServerError;

import java.util.Map;

/**
 * This class represents the exception thrown from the Web service.
 * The Web service should wrap the domain-specific exceptions as an
 * {@link WebServiceException} that can be serialized as the HTTP
 * response with correct status code.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class WebServiceException extends Exception {

	private static final int UnknownError = -1;
	private static final long serialVersionUID = -2176771141361816546L;

	private int errorCode;
	private int statusCode;

	private Map<String, Object> info;

	/**
	 * Construct a <code>WebServiceException</code> instance that wraps
	 * the unexpected exception. In that case, the status code would be
	 * {@link HttpStatusCodes#InternalServerError} and the error code
	 * would be {@link #UnknownError} 
	 * 
	 * @param e the unexpected exception
	 */
	public WebServiceException(Throwable e) {
		this(InternalServerError, UnknownError, e);
	}

	/**
	 * Construct a <code>WebServiceException</code> instance that wraps
	 * the domain-specific exception with the expected status code and
	 * error code.
	 * 
	 * @param status the expected status code
	 * @param error the error code
	 * @param e the domain-specific exception
	 */
	public WebServiceException(int status, int error, Throwable e) {
		super(e.getMessage(), e);
		statusCode = status;
		errorCode = error;
		info = unmodifiableMap(emptyMap());
	}

	/**
	 * Construct a <code>WebServiceException</code> instance with the
	 * status code, domain-specific error code, and the error message.
	 * 
	 * @param status the expected status code
	 * @param error the error code
	 * @param message the error message
	 */
	public WebServiceException(int status, int code, String message) {
		this(status, code, message, emptyMap());
	}

	/**
	 * Construct a <code>WebServiceException</code> instance with the
	 * status code, domain-specific error code, and the error message.
	 * 
	 * @param status the expected status code
	 * @param error the error code
	 * @param message the error message
	 * @param infomration the extra information to the client
	 * @param debugDetail the detail information for debugging
	 */
	public WebServiceException(int status, int code, String message, Map<String, Object> information) {
		super(message);
		errorCode = code;
		statusCode = status;
		info = unmodifiableMap(information);
	}

	/**
	 * Get the status code.
	 * 
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Get the error code.
	 * 
	 * @return the error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Get the extra information to the client. This information
	 * is used to correct the client state and can continue the
	 * original operations, not for debugging.
	 * 
	 * @return the extra information
	 */
	public Map<String, Object> getInfo() {
		return info;
	}
}
