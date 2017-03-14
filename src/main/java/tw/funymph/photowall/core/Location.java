/* Geolocation.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class Location {

	public static final double EARTH_SPHERICAL_RADIUS = 6371;
	
	public static final double MIN_LATITUDE = toRadians(-90d);
	public static final double MAX_LATITUDE = toRadians(90d);
	public static final double MIN_LONGITUDE = toRadians(-180d);
	public static final double MAX_LONGITUDE = toRadians(180d);

	/**
	 * Get a <code>GeoLocation</code> instance with the latitude and longitude
	 * in radian.
	 * 
	 * @param lat the latitude in radian
	 * @param lon the longitude in radian
	 * @return the instance
	 */
	public static Location fromRadians(double lat, double lon) {
		return new Location(toDegrees(lat), toDegrees(lon));
	}

	/**
	 * Get the distance between two locations in km.
	 * 
	 * @param location1 the first location
	 * @param location2 the second location
	 * @return the distance between two locations in km
	 */
	public static double distanceBetween(Location location1, Location location2) {
		return distanceBetween(location1.getLatitude(), location1.getLongitude(), location2.getLatitude(), location2.getLongitude());
	}

	/**
	 * Get the distance between two locations in km.
	 * 
	 * @param latitude1 the latitude of the first location
	 * @param longitude1 the longitude of the first location
	 * @param latitude2 the latitude of the second location
	 * @param longitude2 the longitude of the second location
	 * @return the distance between two location in km
	 */
	public static double distanceBetween(double latitude1, double longitude1, double latitude2, double longitude2) {
		double radLongitude1 = toRadians(longitude1);
		double radLatitude1 = toRadians(latitude1);
		double radLongitude2 = toRadians(longitude2);
		double radLatitude2 = toRadians(latitude2);
		return acos(sin(radLatitude1) * sin(radLatitude2) +
					cos(radLatitude1) * cos(radLatitude2) *
					cos(radLongitude1 - radLongitude2)) * EARTH_SPHERICAL_RADIUS;
	}

	/**
	 * Get the bounding area in which the distance to the center less than
	 * the given distance.
	 * 
	 * @param location the center location
	 * @param distance the required distance
	 * @return the bounding area
	 */
	public static Location[] boundingCoordinates(Location location, double distance) {
		if (distance < 0) {
			throw new IllegalArgumentException("the distance can not be negative");
		}
		double radDistance = distance / EARTH_SPHERICAL_RADIUS;
		double radLongitude = toRadians(location.getLongitude());
		double radLatitude = toRadians(location.getLatitude());
		double minRadLatitude = radLatitude - radDistance;
		double maxRadLatitude = radLatitude + radDistance;
		double minRadLogitude, maxRadLongitude;
		if (minRadLatitude > MIN_LATITUDE && maxRadLatitude < MAX_LATITUDE) {
			double longitudeDelta = asin(sin(radDistance) / cos(radLatitude));
			minRadLogitude = radLongitude - longitudeDelta;
			if (minRadLogitude < MIN_LONGITUDE) {
				minRadLogitude += 2 * PI;
			}
			maxRadLongitude = radLongitude + longitudeDelta;
			if (maxRadLongitude > MAX_LONGITUDE) {
				maxRadLongitude -= 2 * PI;
			}
		}
		else {
			minRadLatitude = max(minRadLatitude, MIN_LATITUDE);
			maxRadLatitude = min(maxRadLatitude, MAX_LATITUDE);
			minRadLogitude = MIN_LONGITUDE;
			maxRadLongitude = MAX_LONGITUDE;
		}
		return new Location[] { fromRadians(minRadLatitude, minRadLogitude), fromRadians(maxRadLatitude, maxRadLongitude) };
	}

	private double latitude;
	private double longitude;
	private String name;

	public Location(double latitude, double longitude) {
		this(latitude, longitude, null);
	}

	public Location(double latitude, double longitude, String name) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}
}
