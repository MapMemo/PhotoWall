/* JsonUtilsTests.java created on Feb 20, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import static org.junit.Assert.*;
import static tw.funymph.photowall.utils.JsonUtils.toJson;
import static tw.funymph.photowall.utils.JsonUtils.toObject;
import static tw.funymph.photowall.utils.JsonUtils.toObjects;
import static tw.funymph.photowall.utils.JsonUtils.toStrings;
import static tw.funymph.photowall.utils.MapUtils.asMap;

import java.util.Map;

import org.junit.Test;

/**
 * This class tests the functionalities of {@link JsonUtils}.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class JsonUtilsTests {

	@Test
	public void testToJson() {
		Map<String, String> object = asMap("key", "value");
		assertEquals("{\"key\":\"value\"}", toJson(object));
	}

	@Test
	public void testToObjectFromString() {
		Map<String, Object> object = toObject("{\"nickname\":\"ABC\",\"id\":\"abc@zyx.com\"}");
		assertEquals(2, object.size());
		assertEquals("abc@zyx.com", object.get("id"));
		assertEquals("ABC", object.get("nickname"));
	}

	@Test
	public void testToObjectFromStream() {
		Map<String, Object> object = toObject(getClass().getResourceAsStream("object.json"));
		assertEquals(2, object.size());
		assertEquals("abc@zyx.com", object.get("id"));
		assertEquals("ABC", object.get("nickname"));
	}

	@Test
	public void testToObjectsFromStream() {
		Object[] objects = toObjects(getClass().getResourceAsStream("objects.json"));
		assertEquals(3, objects.length);
		assertEquals("111", objects[0]);
		assertEquals("112", objects[1]);
		assertEquals("121", objects[2]);
	}

	@Test
	public void testToObjectsFromString() {
		Object[] objects = toObjects("[ 111, 112, 114 ]");
		assertEquals(3, objects.length);
		assertEquals(111.0, objects[0]);
		assertEquals(112.0, objects[1]);
		assertEquals(114.0, objects[2]);
	}

	@Test
	public void testToStringsFromStream() {
		String[] strings = toStrings(getClass().getResourceAsStream("objects.json"));
		assertEquals(3, strings.length);
		assertEquals("111", strings[0]);
		assertEquals("112", strings[1]);
		assertEquals("121", strings[2]);
	}

	@Test
	public void testToStringsFromString() {
		String[] strings = toStrings("[ \"111\", \"112\", \"114\" ]");
		assertEquals(3, strings.length);
		assertEquals("111", strings[0]);
		assertEquals("112", strings[1]);
		assertEquals("114", strings[2]);
	}
}
