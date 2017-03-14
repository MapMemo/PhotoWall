/* DefaultPhotoManager.java created on Mar 3, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

import tw.funymph.photowall.core.repository.PhotoRepository;
import tw.funymph.photowall.core.repository.RepositoryException;

/**
 * @author spirit
 * @version 
 * @since 
 */
public class DefaultPhotoManager implements PhotoManager {

	private PhotoRepository repository;

	/**
	 * 
	 */
	public DefaultPhotoManager(PhotoRepository repository) {
		this.repository = repository;
	}

	@Override
	public Photo addPhoto(String posterId, String photoId, long timestamp, Location location) {
		Photo photo = new Photo(photoId, posterId, timestamp, location);
		try {
			repository.save(photo);
		}
		catch (RepositoryException e) {
			e.printStackTrace();
		}
		return photo;
	}

	@Override
	public Photo[] getPhotos(Long before, Long after) {
		return repository.getPhotos(before, after);
	}

	@Override
	public Photo[] getNearbyPhotos(double latitude, double longitude, double distance) {
		return repository.getNearbyPhotos(latitude, longitude, distance);
	}
}
