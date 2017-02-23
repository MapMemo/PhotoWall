/* HttpHeaders.java created on Feb 20, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;

/**
 * This interface define the constants for HTTP headers.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface HttpHeaders {

	// Cache control
	public static final String ETag = "ETag";
	public static final String IfNoneMatch = "If-None-Match";

	// Content negotiation
	public static final String Accept = "Accept";
	public static final String AcceptCharset = "Accept-Charset";
	public static final String AcceptEncoding = "Accept-Encoding";
	public static final String AcceptLanguage = "Accept-Language";
	public static final String AcceptDatetime = "Accept-Datetime";

	public static final String ContentType = "Content-Type";
	public static final String ContentLength = "Content-Length";
	public static final String ContentMD5 = "Content-MD5";
	public static final String ContentDisposition = "Content-Disposition";

	// Authentication
	public static final String AuthToken = "Auth-Token";
	public static final String Authorization = "Authorization";

	// For CORS
	public static final String AccessControlAllowOrigin = "Access-Control-Allow-Origin";
	public static final String AccessControlAllowMethods = "Access-Control-Allow-Methods";
	public static final String AccessControlAllowHeaders = "Access-Control-Allow-Headers";
	public static final String AccessControlRequestMethod = "Access-Control-Request-Method";
	public static final String AccessControlRequestHeaders = "Access-Control-Request-Headers";

	// Custom non-standard HTTPs
	public static final String Elapsed = "Elapsed";
	public static final String Timestamp = "Timestamp";

	/**
	 * Get the filename from disposition content of HTTP header
	 *
	 * @param disposition the disposition header
	 * @return the filename
	 */
	public static String contentDispositionFilename(String disposition) {
		Matcher matcher = compile("attachment[\\s;]+filename=\"(.+)\"").matcher(disposition);
		if (!matcher.find()) {
			throw new IllegalArgumentException("attachment filename can't be identified");
		}
		return matcher.group(1);
	}
}
