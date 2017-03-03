/* PhotoRepository.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core.repository;

import java.util.List;

import tw.funymph.photowall.core.Photo;

/**
 * @author spirit
 * @version 
 * @since 
 */
public interface PhotoRepository {

	public List<Photo> allPhotos();

	public void save(Photo photo) throws RepositoryException;
}
