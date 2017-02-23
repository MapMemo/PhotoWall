/* AccountWebService.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws.auth;

import static java.lang.String.format;
import static java.nio.file.Files.createDirectories;
import static spark.Spark.post;
import static tw.funymph.photowall.utils.IOUtils.copy;
import static tw.funymph.photowall.utils.IOUtils.toMD5;
import static tw.funymph.photowall.utils.StringUtils.assertNotBlank;
import static tw.funymph.photowall.utils.StringUtils.equalsIgnoreCase;
import static tw.funymph.photowall.ws.auth.AccountFormatter.publicInfo;
import static tw.funymph.photowall.ws.HttpHeaders.contentDispositionFilename;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

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

	public Object changePortrait(Request request, Response response) throws Exception {
		if (!equalsIgnoreCase(BinaryOctetStream, request.headers(ContentType))) {
			throw new WebServiceException(NotAcceptable, -1, format("the content type must be %s", BinaryOctetStream));
		}
		String contentDisposition = assertNotBlank(request.headers(ContentDisposition), "the content-disposition is required");
		Path path = Paths.get("files", contentDispositionFilename(contentDisposition));
		createDirectories(path.getParent());
		copy(request.raw().getInputStream(), new FileOutputStream(path.toFile()));
		response.header(ETag, toMD5(path.toFile()));
		return null;
	}

	@Override
	public void routes() {
		post("/accounts", metaAware(this::register));
		post("/accounts/portrait", metaAware(this::changePortrait));
	}
}
