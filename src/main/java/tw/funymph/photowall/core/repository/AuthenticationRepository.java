/* AuthenticationRepository.java created on Mar 1, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core.repository;

import tw.funymph.photowall.core.Authentication;

/**
 * This interface define the methods to access the {@link Authentication} objects
 * from the persistence storage.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface AuthenticationRepository {

	/**
	 * Get the authentication specified by the token (not identity).
	 * 
	 * @param token the access token
	 * @return the authentication if any error occurred
	 */
	public Authentication get(String token);

	/**
	 * Save the authentication.
	 * 
	 * @param authentication the authentication to save
	 * @throws RepositoryException if any error occurred
	 */
	public void save(Authentication authentication) throws RepositoryException;

	/**
	 * Remove the authentication specified by the token (not identity).
	 *  
	 * @param token the access token
	 * @throws RepositoryException if any error occurred
	 */
	public void delete(String token) throws RepositoryException;
}
