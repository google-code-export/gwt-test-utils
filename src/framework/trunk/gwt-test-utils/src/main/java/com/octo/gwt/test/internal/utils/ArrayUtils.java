package com.octo.gwt.test.internal.utils;

import java.util.HashMap;
import java.util.Map;

public class ArrayUtils {

	private ArrayUtils() {

	}

	public static boolean contains(Object[] array, Object valueToFind) {
		for (int i = 0; i < array.length; i++) {
			if (valueToFind.equals(array[i])) {
				return true;
			}
		}
		return false;
	}

	public static <K, V> Map<K, V> copyMap(Map<K, V> map) {
		Map<K, V> result = new HashMap<K, V>();

		for (Map.Entry<K, V> entry : map.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

}
