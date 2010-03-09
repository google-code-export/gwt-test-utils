package com.octo.gwt.test.internal;

public class PatchDOM {

	private static int counter = 0;

	public static String createUniqueId() {
		counter++;
		return "elem_" + Long.toString(counter);
	}

}
