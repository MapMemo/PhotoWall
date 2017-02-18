/* WebServiceException.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static java.util.Collections.unmodifiableMap;

import java.util.Map;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class WebServiceException extends Exception {

	private static final long serialVersionUID = -4325282116410151770L;

	private int errorCode;
	private int statusCode;

	private Map<String, Object> info;
	private Map<String, Object> detail;

	/**
	 * 
	 */
	public WebServiceException(Exception e) {
		super("Unexpected internal server error", e);
		statusCode = 500;
		errorCode = -1;
	}

	/**
	 * 
	 */
	public WebServiceException(int status, int code, String message) {
		this(status, code, message, null, null);
	}

	/**
	 * 
	 */
	public WebServiceException(int status, int code, String message, Map<String, Object> information, Map<String, Object> debugDetail) {
		super(message);
		errorCode = code;
		statusCode = status;
		info = unmodifiableMap(information);
		detail = unmodifiableMap(debugDetail);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public Map<String, Object> getDetail() {
		return detail;
	}
}
