/* PhotoWall.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall;

import static spark.Spark.*;

import tw.funymph.photowall.ws.auth.AccountWebService;

/**
 * The main entry of the PhotoWall Web service.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class PhotoWall {

	public static void main(String[] args) {
		path("/ws", () -> {
			new AccountWebService().routes();
		});
	}
}
