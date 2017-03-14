/* PhotoRepository.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core.repository;

import tw.funymph.photowall.core.Photo;

/**
 * @author spirit
 * @version 
 * @since 
 */
public interface PhotoRepository {

	public Photo[] getNearbyPhotos(double latitude, double longitude, double distance);

	public Photo[] getPhotos(Long before, Long after);

	public void save(Photo photo) throws RepositoryException;
}
