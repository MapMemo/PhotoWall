/* PhotoWebService.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws.photo;

import static spark.Spark.post;

import spark.Request;
import spark.Response;
import tw.funymph.photowall.core.PhotoManager;
import tw.funymph.photowall.ws.SparkWebService;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class PhotoWebService implements SparkWebService {

	private PhotoManager photoManager;

	public PhotoWebService(PhotoManager photoManager) {
		this.photoManager = photoManager;
	}

	@Override
	public void routes() {
		post("/photos", metaAware(this::uploadPhoto));
	}

	public Object uploadPhoto(Request request, Response response) throws Exception {
		// TODO: get account's identity from the information in request header
		photoManager.addPhoto("aaa", "bbb", System.currentTimeMillis());
		return null;
	}
}
