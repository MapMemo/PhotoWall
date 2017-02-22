/* AccountWebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws.auth;

import static spark.Spark.post;
import static tw.funymph.photowall.ws.auth.AccountFormatter.publicInfo;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import spark.Request;
import spark.Response;
import tw.funymph.photowall.core.AccountManager;
import tw.funymph.photowall.core.AccountManagerException;
import tw.funymph.photowall.ws.SparkWebService;
import tw.funymph.photowall.ws.WebServiceException;

/**
 * This class handles the requests to <code>/ws/accounts/*</code>.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class AccountWebService implements SparkWebService {

	private AccountManager accountManager;

	/**
	 * Construct a <code>AccountWebService</code> instance with the
	 * account manager (main controller).
	 * 
	 * @param manager the account manager
	 */
	public AccountWebService(AccountManager manager) {
		accountManager = manager;
	}

	/**
	 * This method handles <code>POST /ws/accounts</code> request to register a new account.
	 * 
	 * @param request the request
	 * @param response the response
	 * @return the response body
	 * @throws Exception if any error occurs
	 */
	public Object register(Request request, Response response) throws Exception {
		try {
			RegistrationRequest registration = new Gson().fromJson(request.body(), RegistrationRequest.class);
			registration.validate();
			return publicInfo(accountManager.register(registration.getIdentity(), registration.getNickname(), registration.getPassword()));
		}
		catch (JsonSyntaxException e) {
			throw new WebServiceException(BadRequest, -1, "invalid request format");
		}
		catch (AccountManagerException e) {
			throw new WebServiceException(BadRequest, -1, e.getMessage());
		}
	}

	@Override
	public void routes() {
		post("/accounts", metaAware(this::register));
	}
}
