/* PhotoWall.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall;

import static spark.Spark.*;
import static tw.funymph.photowall.utils.JsonUtils.toJson;
import static tw.funymph.photowall.ws.HttpContentTypes.ApplicationJson;
import static tw.funymph.photowall.wss.WebSocketEventHandler.EventPath;

import tw.funymph.photowall.ws.MetaAwareResult;
import tw.funymph.photowall.ws.WebServiceException;
import tw.funymph.photowall.ws.auth.AccountWebService;
import tw.funymph.photowall.wss.WebSocketEventHandler;

/**
 * The main entry of the PhotoWall Web service.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class PhotoWall {

	public static void main(String[] args) {
		webSocket(EventPath, WebSocketEventHandler.class);
		path("/ws", () -> {
			new AccountWebService().routes();
		});
		notFound((request, response) -> {
			response.type(ApplicationJson);
			MetaAwareResult metaAwareResult = new MetaAwareResult();
			return toJson(metaAwareResult.fail(request.pathInfo(), request.requestMethod(), new WebServiceException(404, -1, "no action for the path")));
		});
	}
}
