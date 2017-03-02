/* IOUtilsTests.java created on Mar 2, 2016.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static tw.funymph.photowall.utils.IOUtils.closeSilently;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;

/**
 * This class tests the functionalities of {@link IOUtils}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class IOUtilsTests {

	@Test
	public void testCloseSilently() {
		try {
			closeSilently(null);
		}
		catch (Throwable e) {
			fail("no exception should be thrown");
		}

		InputStream stream = new ByteArrayInputStream(new byte[1024]);
		try {
			closeSilently(stream);
		}
		catch (Throwable e) {
			fail("no exception should be thrown");
		}
	}

	@Test
	public void testCloseSilentlyWithException() throws Exception {
		InputStream stream = mock(InputStream.class);
		doThrow(IOException.class).when(stream).close();
		try {
			IOUtils.closeSilently(stream);
		}
		catch (Throwable e) {
			fail("no exception should be thrown");
		}
	}

	@Test
	public void testCopyInputStreamtoOutputStream() throws Exception {
		InputStream source = getClass().getResourceAsStream("md5TestFile");
		File temp = new File("temp");
		temp.deleteOnExit();
		FileOutputStream target = new FileOutputStream(temp);
		assertTrue(IOUtils.copy(source, target) > 0);
		FileInputStream stream = new FileInputStream(temp);
		assertEquals("This is a test file for testing IOUtils.", IOUtils.toString(stream));
		
		assertEquals(IOUtils.EOF, IOUtils.copy(null, target));
		assertEquals(IOUtils.EOF, IOUtils.copy(source, null));
		assertEquals(IOUtils.EOF, IOUtils.copy(source, target, null));

		InputStream erroneousStream = new ErroneousInputStream();
		assertEquals(IOUtils.EOF, IOUtils.copy(erroneousStream, target));
	}

	@Test
	public void testToInputStreamFromString() {
		InputStream stream = IOUtils.toInputStream("This is a string to become input stream.");
		assertEquals("This is a string to become input stream.", IOUtils.toString(stream));

		assertNull(IOUtils.toInputStream(null, Charset.forName("UTF-8")));
		assertNull(IOUtils.toInputStream("No charset", null));
	}

	@Test
	public void testToStringFromInputStream() {
		assertEquals("", IOUtils.toString(null));
		assertEquals("This is a test file for testing IOUtils.", IOUtils.toString(getClass().getResourceAsStream("md5TestFile")));
	}

	@Test
	public void testToMD5File() throws Exception {
		File file = null;
		assertNull(IOUtils.toMD5(file));
		file = new File(getClass().getResource("md5TestFile").toURI());
		assertEquals("3daeaf72bf04e3b2992554ddd6715a79", IOUtils.toMD5(file));
		assertNull(IOUtils.toMD5(new File("notExisting")));

		InputStream nullStream = null;
		assertNull(IOUtils.toMD5(nullStream));

		InputStream erroneousStream = new ErroneousInputStream();
		assertNull(IOUtils.toMD5(erroneousStream));
	}
}
