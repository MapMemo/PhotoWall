/* PhotoManager.java created on Mar 3, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

/**
 * @author spirit
 * @version 
 * @since 
 */
public interface PhotoManager {

	public Photo[] getNearbyPhotos(double latitude, double longitude, double distance);

	public Photo[] getPhotos(Long before, Long after);

	public Photo addPhoto(String posterId, String photoId, long timestamp, Location location);
}
