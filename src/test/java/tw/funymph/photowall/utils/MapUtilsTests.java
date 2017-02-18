/* MapUtilsTests.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import static org.junit.Assert.*;
import static tw.funymph.photowall.utils.MapUtils.asMap;

import java.util.Map;

import org.junit.Test;

/**
 * This class tests the functionalities of {@link MapUtils}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class MapUtilsTests {

	@Test
	public void test() {
		Map<String, String> result = asMap("key", "value");
		assertEquals("value", result.get("key"));
		assertEquals(1, result.size());
	}
}
