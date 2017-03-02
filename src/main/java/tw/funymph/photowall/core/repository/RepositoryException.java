/* RepositoryException.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core.repository;

/**
 * This class represents all possible exceptions thrown by repositories.
 * The repositories should wrap all kinds of underlying exceptions into
 * this type to avoid abstraction leak.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class RepositoryException extends Exception {

	private static final long serialVersionUID = 443078351693683658L;

	/**
	 * Construct a <code>RepositoryException</code> instance with the
	 * error message and the underlying exception.
	 * 
	 * @param message the error message
	 * @param e the underlying exception
	 */
	public RepositoryException(String message, Throwable e) {
		super(message, e);
	}
}
