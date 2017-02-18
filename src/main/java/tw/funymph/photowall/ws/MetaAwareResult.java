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
 * @author spirit
 * @version 
 * @since 
 */
public class MetaAwareResult {

	private long timestamp;

	private boolean needDetail;

	private Map<String, Object> meta;
	private Map<String, Object> result;

	public MetaAwareResult() {
		this(true);
	}

	public MetaAwareResult(boolean detailed) {
		needDetail = detailed;
		timestamp = currentTimeMillis();
		meta = asMap("timestamp", timestamp);
		result = asMap("meta", meta);
	}

	public Map<String, Object> succeed(Object data) {
		if (data != null) {
			result.put("data", data);
		}
		return finailized();
	}

	public Map<String, Object> fail(String path, String method, WebServiceException e) {
		Map<String, Object> error = asMap("path", path);
		error.put("method", method);
		error.put("code", e.getErrorCode());
		error.put("message", e.getMessage());
		error.put("status", e.getStatusCode());
		if (notEmpty(e.getInfo())) {
			error.put("info", e.getInfo());
		}
		embedDetailIfNeed(error, e);
		result.put("error", error);
		return finailized();
	}

	private void embedDetailIfNeed(Map<String, Object> error, WebServiceException e) {
		if (needDetail) {
			Map<String, Object> detail = asMap("traces", stream(e.getStackTrace()).map(this::formatTrace).toArray());
			if (notEmpty(e.getDetail())) {
				detail.putAll(e.getDetail());
			}
			error.put("detail", detail);
		}
	}

	private String formatTrace(StackTraceElement trace) {
		return format("at %s.%s(%s.%d)", trace.getClassName(), trace.getMethodName(), trace.getFileName(), trace.getLineNumber());
	}

	private Map<String, Object> finailized() {
		meta.put("elapsed", (currentTimeMillis() - timestamp));
		return unmodifiableMap(result);
	}
}
