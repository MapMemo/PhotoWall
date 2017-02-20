/* JsonUtils.java created on Feb 20, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import static java.nio.charset.Charset.forName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This class provides some helpful utility methods for JSON.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface JsonUtils {

	static final Charset UTF8 = forName("UTF-8");

	/**
	 * Convert the object to JSON string.
	 * 
	 * @param object the object to convert
	 * @return the JSON string
	 */
	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	/**
	 * Convert the JSON string to an object (a Map).
	 * 
	 * @param json the JSON string
	 * @return the object
	 */
	public static Map<String, Object> toObject(String json) {
		return toObject(new ByteArrayInputStream(json.getBytes(UTF8)));
	}

	/**
	 * Convert the JSON stream to an object (a Map).
	 * 
	 * @param json the JSON string
	 * @return the object
	 */
	public static Map<String, Object> toObject(InputStream stream) {
		TypeToken<Map<String, Object>> map = new TypeToken<Map<String, Object>>() {};
		return new Gson().fromJson(new InputStreamReader(stream), map.getType());
	}

	/**
	 * Convert the JSON string to an object array.
	 * 
	 * @param json the JSON string
	 * @return the object array
	 */
	public static Object[] toObjects(String json) {
		return toObjects(new ByteArrayInputStream(json.getBytes(UTF8)));
	}

	/**
	 * Convert the JSON string to an object array.
	 * 
	 * @param json the JSON string
	 * @return the object array
	 */
	public static Object[] toObjects(InputStream stream) {
		TypeToken<Object[]> array = new TypeToken<Object[]>() {};
		return new Gson().fromJson(new InputStreamReader(stream), array.getType());
	}

	/**
	 * Convert the JSON string to a string array.
	 * 
	 * @param json the JSON string
	 * @return the string array
	 */
	public static String[] toStrings(String json) {
		return toStrings(new ByteArrayInputStream(json.getBytes(UTF8)));
	}

	/**
	 * Convert the JSON string to a string array.
	 * 
	 * @param json the JSON string
	 * @return the string array
	 */
	public static String[] toStrings(InputStream stream) {
		TypeToken<String[]> array = new TypeToken<String[]>() {};
		return new Gson().fromJson(new InputStreamReader(stream), array.getType());
	}
}
