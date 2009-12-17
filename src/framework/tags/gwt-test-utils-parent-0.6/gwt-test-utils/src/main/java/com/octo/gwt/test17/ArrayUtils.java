package com.octo.gwt.test17;

public class ArrayUtils {

	public static boolean contains(Object[] array, Object valueToFind) {
		for (int i = 0; i < array.length; i++) {
			if (valueToFind.equals(array[i])) {
				return true;
			}
		}
		return false;
	}

}
