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

	private String id;
	private String posterId;

	private long timestamp;
	private Location location;

	public Photo(String id, String posterId, long timestamp) {
		this(id, posterId, timestamp, null);
	}

	public Photo(String id, String posterId, long timestamp, Location location) {
		this.id = id;
		this.posterId = posterId;
		this.timestamp = timestamp;
		this.location = location;
	}

	public String getId() {
		return id;
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
