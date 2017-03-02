/* ErroneousInputStream.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is a class for testing I/O exception handling.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class ErroneousInputStream extends InputStream {

	@Override
	public int read() throws IOException {
		throw new IOException();
	}
}
