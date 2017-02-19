/* MetaAwareResult.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableMap;
import static tw.funymph.photowall.utils.MapUtils.asMap;
import static tw.funymph.photowall.utils.MapUtils.notEmpty;

import java.util.Map;

/**
 * This class wraps the result and exception with meta data for
 * measurement.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class MetaAwareResult {

	long elapsed;
	long timestamp;

	private boolean needDetail;

	private Map<String, Object> meta;
	private Map<String, Object> result;

	/**
	 * Construct a <code>MetaAwareResult</code> instance to start
	 * recording the execution time.
	 */
	public MetaAwareResult() {
		this(false);
	}

	/**
	 * Construct a <code>MetaAwareResult</code> instance to start recording
	 * the execution time.
	 * 
	 * @param detailed {@code true} to embed details of the exception in the result
	 */
	public MetaAwareResult(boolean detailed) {
		needDetail = detailed;
		timestamp = currentTimeMillis();
		meta = asMap("timestamp", timestamp);
		result = asMap("meta", meta);
	}

	/**
	 * Call this to wrap the succeeded result with meta data.
	 * 
	 * @param data the succeeded data; can be {@code null}
	 * @return the wrapped result
	 */
	public Map<String, Object> succeed(Object data) {
		if (data != null) {
			result.put("data", data);
		}
		return finailized();
	}

	/**
	 * Call this to wrap the exception with meta data.
	 * 
	 * @param path the request path
	 * @param method the request method
	 * @param e the exception
	 * @return the wrapped result
	 */
	public Map<String, Object> fail(String path, String method, WebServiceException e) {
		Map<String, Object> error = wrapException(path, method, e);
		result.put("error", error);
		return finailized();
	}

	/**
	 * Wrap the basic information of the exception into a map object.
	 *  
	 * @param path the request path
	 * @param method the request method
	 * @param e the exception
	 * @return the basic information map
	 */
	private Map<String, Object> wrapException(String path, String method, WebServiceException e) {
		Map<String, Object> error = asMap("path", path);
		error.put("method", method);
		error.put("code", e.getErrorCode());
		error.put("message", e.getMessage());
		error.put("status", e.getStatusCode());
		if (notEmpty(e.getInfo())) {
			error.put("info", e.getInfo());
		}
		if (needDetail) {
			error.put("traces", stream(e.getStackTrace()).map(this::formatTrace).toArray());
		}
		return error;
	}

	/**
	 * The method reference lambda to translate stack trace element to a simple string.
	 * 
	 * @param trace the stack trace element
	 * @return the simple string
	 */
	private String formatTrace(StackTraceElement trace) {
		return format("at %s.%s(%s.%d)", trace.getClassName(), trace.getMethodName(), trace.getFileName(), trace.getLineNumber());
	}

	/**
	 * Get the finalized (unmodifiable) meta attached result.
	 * 
	 * @return the finalized result
	 */
	private Map<String, Object> finailized() {
		elapsed = currentTimeMillis() - timestamp;
		meta.put("elapsed", elapsed);
		return unmodifiableMap(result);
	}
}
