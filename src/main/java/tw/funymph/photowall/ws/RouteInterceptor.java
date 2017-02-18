/* RouteInterceptor.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws;

import spark.Route;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
@FunctionalInterface
public interface RouteInterceptor {

	public Route intercept(Route route);
}
