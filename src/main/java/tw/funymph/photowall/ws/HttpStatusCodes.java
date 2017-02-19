/* HttpStatusCodes.java created on 2017年2月19日.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

/**
 * This interface list some of the HTTP status codes that are
 * common used in Web services.
 *  
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface HttpStatusCodes {

	public static final int OK = 200;
	public static final int Created = 201;
	public static final int Accepted = 202;

	public static final int MovedPermanently = 301;
	public static final int NotModified = 304;

	public static final int BadRequest = 400;
	public static final int Unauthorized = 401;
	public static final int PaymentRequired = 402;
	public static final int Forbidden = 403;
	public static final int NotFound = 404;
	public static final int MethodNotAllowed = 405;
	public static final int NotAcceptable = 406;
	public static final int RequestTimeout = 408;
	public static final int Conflict = 409;
	public static final int UnsupportedMediaType = 415;

	public static final int InternalServerError = 500;
}
