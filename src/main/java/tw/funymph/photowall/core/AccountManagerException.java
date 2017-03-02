/* AccountManagerException.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.core;

/**
 * This class represents all possible exceptions thrown by {@link AccountManager}.
 * The {@link AccountManager} will wrap all kinds of underlying exceptions into
 * this type.
 *  
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class AccountManagerException extends Exception {

	private static final long serialVersionUID = -9192538953944965988L;

	/**
	 * Construct a <code>AccountManagerException</code> instance with
	 * the given error message.
	 * 
	 * @param message the error message
	 */
	public AccountManagerException(String message) {
		super(message);
	}

	/**
	 * Construct a <code>AccountManagerException</code> instance with
	 * the given error message and underlying exception.
	 * 
	 * @param message the error message
	 * @param e the underlying exception
	 */
	public AccountManagerException(String message, Throwable e) {
		super(message, e);
	}
}
