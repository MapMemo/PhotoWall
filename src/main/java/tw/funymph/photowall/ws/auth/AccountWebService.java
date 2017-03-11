/* AccountWebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws.auth;

import static java.lang.String.format;
import static java.nio.file.Files.createDirectories;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static spark.Spark.*;
import static tw.funymph.photowall.utils.IOUtils.copy;
import static tw.funymph.photowall.utils.IOUtils.toMD5;
import static tw.funymph.photowall.utils.StringUtils.assertNotBlank;
import static tw.funymph.photowall.utils.StringUtils.equalsIgnoreCase;
import static tw.funymph.photowall.ws.auth.AccountFormatter.publicInfo;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import spark.Request;
import spark.Response;
import tw.funymph.photowall.core.Account;
import tw.funymph.photowall.core.AccountManager;
import tw.funymph.photowall.core.AccountManagerException;
import tw.funymph.photowall.core.Authentication;
import tw.funymph.photowall.ws.HttpHeaders;
import tw.funymph.photowall.ws.SparkWebService;
import tw.funymph.photowall.ws.WebServiceException;

/**
 * This class handles the requests to <code>/ws/accounts/*</code> and <code>/ws/authentications/*</code>.
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

	@Override
	public void routes() {
		post("/accounts", metaAware(this::register));
		post("/me/portrait", metaAware(validToken(this::changePortrait)));
		post("/authentications", metaAware(this::login));
		get("/portraits/:id", metaAware(this::getPortrait));
		get("/profiles/:id", metaAware(validToken(this::getAccount)));
		get("/profiles", metaAware(validToken(this::getAccounts)));
		delete("/authentications/mine", metaAware(this::logout));
	}

	/**
	 * This method handles <code>POST /ws/accounts</code> request to register
	 * a new account.
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
			Account account = accountManager.register(registration.getEamil(), registration.getNickname(), registration.getPassword());
			Authentication authentication = accountManager.login(registration.getEamil(), registration.getPassword());
			response.header(AuthToken, authentication.getToken());
			return publicInfo(account);
		}
		catch (JsonSyntaxException e) {
			throw new WebServiceException(BadRequest, -1, "invalid request format");
		}
		catch (AccountManagerException e) {
			throw new WebServiceException(BadRequest, -1, e.getMessage());
		}
	}

	/**
	 * This method handles <code>POST /ws/authentications</code> request to login
	 * as an existing account.
	 * 
	 * @param request the request
	 * @param response the response
	 * @return {@code null} the login information is attached in the response header
	 * @throws Exception if any error occurs
	 */
	public Object login(Request request, Response response) throws Exception {
		String[] credentials = HttpHeaders.basicAuthorization(request.headers(Authorization));
		if (credentials == null || credentials.length != 2) {
			throw new AccountManagerException("invalid basic authorization information");
		}
		Authentication authentication = accountManager.login(credentials[0], credentials[1]);
		response.header(AuthToken, authentication.getToken());
		return publicInfo(accountManager.checkAccount(authentication.getToken()));
	}

	public Object logout(Request request, Response response) throws Exception {
		String token = request.headers(AuthToken);
		accountManager.logout(token);
		return null;
	}

	/**
	 * This method handles <code>POST /ws/me/portrait</code> request to change
	 * his/her portrait.
	 * 
	 * @param request the request body
	 * @param response the response
	 * @return {@code null} the uploaded checksum is attached in the response header
	 * @throws Exception if any error occurred
	 */
	public Object changePortrait(Request request, Response response) throws Exception {
		if (!equalsIgnoreCase(BinaryOctetStream, request.headers(ContentType))) {
			throw new WebServiceException(NotAcceptable, -1, format("the content type must be %s", BinaryOctetStream));
		}
		Account account = authenticatedAccount(request);
		Path path = Paths.get("files", "portraits", account.getId());
		createDirectories(path.getParent());
		copy(request.raw().getInputStream(), new FileOutputStream(path.toFile()));
		response.header(ETag, toMD5(path.toFile()));
		return null;
	}

	public Object getPortrait(Request request, Response response) throws Exception {
		String id = request.params("id");
		assertNotBlank(id, "the id is not specified");
		response.redirect(format("/portraits/%s", id));
		return null;
	}

	public Object getAccounts(Request request, Response response) throws Exception {
		Account[] accounts = accountManager.getAll();
		return stream(accounts).map(AccountFormatter::publicInfo).collect(toList());
	}

	public Object getAccount(Request request, Response response) throws Exception {
		String id = request.params("id");
		assertNotBlank(id, "the id is not specified");
		Account account = accountManager.getAccount(id);
		if (account == null) {
			throw new WebServiceException(404, -1, "the account does not exist");
		}
		return publicInfo(account);
	}
}
