/* RegistrationRequest.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws.auth;

import static tw.funymph.photowall.utils.StringUtils.isBlank;
import static tw.funymph.photowall.ws.HttpStatusCodes.BadRequest;

import tw.funymph.photowall.ws.WebServiceException;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class RegistrationRequest {

	private String email;
	private String nickname;
	private String password;

	/**
	 * @return the email
	 */
	public String getEamil() {
		return email;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	public void validate() throws WebServiceException {
		if (isBlank(email) || isBlank(nickname) || isBlank(password)) {
			throw new WebServiceException(BadRequest, -1, "invalid request format");
		}
	}
}
