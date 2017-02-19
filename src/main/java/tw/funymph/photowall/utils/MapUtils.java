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
 * @author spirit
 * @version 
 * @since 
 */
public interface MapUtils {

	public static <K, V> Map<K, V> asMap(K key, V value) {
		Map<K, V> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	public static <K, V> boolean notEmpty(Map<K, V> map) {
		return (map != null)? map.size() > 0 : false; 
	}
}
