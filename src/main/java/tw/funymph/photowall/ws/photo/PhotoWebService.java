/* PhotoWebService.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws.photo;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;
import static java.util.regex.Pattern.compile;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spark.Request;
import spark.Response;
import tw.funymph.photowall.core.Location;
import tw.funymph.photowall.core.Photo;
import tw.funymph.photowall.core.PhotoManager;
import tw.funymph.photowall.ws.SparkWebService;
import tw.funymph.photowall.ws.WebServiceException;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class PhotoWebService implements SparkWebService {

	private static final String GeolocationPattern = "geo:(.+),(.+)";
	private static final String GeolocationWithDistancePattern = "geo:(.+),(.+);r=(.+)";

	private PhotoManager photoManager;

	public PhotoWebService(PhotoManager photoManager) {
		this.photoManager = photoManager;
	}

	@Override
	public void routes() {
		post("/photos", metaAware(validToken(this::uploadPhoto)));
		get("/photos", metaAware(validToken(this::getPhotos)));
		get("/photos/nearby", metaAware(validToken(this::getNearbyPhotos)));
	}

	public Object getPhotos(Request request, Response response) throws Exception {
		Long before = (request.queryParams("before") != null) ? parseLong(request.queryParams("before")) : null;
		Long after = (request.queryParams("after") != null) ? parseLong(request.queryParams("after")) : null;
		String posterId = request.queryParams("poster");
		return photoManager.getPhotos(before, after, posterId);
	}

	public Object getNearbyPhotos(Request request, Response response) throws Exception {
		Matcher matcher = compile(GeolocationWithDistancePattern).matcher(request.headers(Geolocation));
		if (!matcher.matches()) {
			throw new WebServiceException(NotAcceptable, -1, "the geolocation is not provided");
		}
		double latitude = parseDouble(matcher.group(1));
		double longitude = parseDouble(matcher.group(2));
		double distance = parseDouble(matcher.group(3));
		assertValueInsideRange(latitude, -90, 90, "the latitude must be inside -90 to 90");
		assertValueInsideRange(longitude, -180, 180, "the latitude must be inside -180 to 180");
		assertPositiveDistance(distance);
		return photoManager.getNearbyPhotos(latitude, longitude, distance);
	}

	public Object uploadPhoto(Request request, Response response) throws Exception {
		String posterId = authenticatedAccount(request).getId();
		Location location = parseLocation(request);
		long timestamp = currentTimeMillis();
		String photoId = randomUUID().toString();
		saveFile(request, "photos", photoId);
		Photo photo = photoManager.addPhoto(posterId, photoId, timestamp, location);
		return photo;
	}

	static Location parseLocation(Request request) {
		Matcher matcher = Pattern.compile(GeolocationPattern).matcher(request.headers(Geolocation));
		if (matcher.matches()) {
			double latitude = parseDouble(matcher.group(1));
			double longitude = parseDouble(matcher.group(2));
			String name = request.headers(Placemark);
			return new Location(latitude, longitude, name);
		}
		return null;
	}

	/**
	 * Assert the value inside the given range.
	 * 
	 * @param value the value to check
	 * @param min the minimum value of the range
	 * @param max the maximum value of the range
	 * @param message the exception message if the value is not inside the range
	 * @throws IllegalArgumentException if the value is not inside the range
	 */
	private void assertValueInsideRange(double value, double min, double max, String message) {
		if (value < min || value > max) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Assert the distance is positive.
	 * 
	 * @param distance the distance to assert
	 * @throws IllegalArgumentException if the distance is negative
	 */
	private void assertPositiveDistance(double distance) {
		if (distance < 0) {
			throw new IllegalArgumentException("the distnace can not be negative");
		}
	}
}
