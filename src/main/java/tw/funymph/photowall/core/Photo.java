/* Photo.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class Photo {

	private String photoId;
	private String posterId;

	private long timestamp;
	private Location location;

	public Photo(String photoId, String posterId, long timestamp) {
		this(photoId, posterId, timestamp, null);
	}

	public Photo(String photoId, String posterId, long timestamp, double latitude, double longitude) {
		this(photoId, posterId, timestamp, new Location(latitude, longitude));
	}

	public Photo(String photoId, String posterId, long timestamp, Location location) {
		this.photoId = photoId;
		this.posterId = posterId;
		this.timestamp = timestamp;
		this.location = location;
	}

	public String getPhotoId() {
		return photoId;
	}

	public String getPosterId() {
		return posterId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Location getLocation() {
		return location;
	}
}
