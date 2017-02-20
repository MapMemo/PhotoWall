/* MapUtils.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides some helpful utility methods for Map.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public interface MapUtils {

	/**
	 * Wrap the single key and value pair as a map.
	 * 
	 * @param key the key
	 * @param value the value
	 * @return the single key-value pair map
	 */
	public static <K, V> Map<K, V> asMap(K key, V value) {
		Map<K, V> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	/**
	 * Check whether the map is not empty.
	 * 
	 * @param map the map to check
	 * @return {@code true} if the map is not {@code null} and not empty
	 */
	public static <K, V> boolean notEmpty(Map<K, V> map) {
		return (map != null)? map.size() > 0 : false; 
	}
}
