package com.octo.gwt.test17.internal;

public class PatchDOM {

	private static int counter = 0;

	public static String createUniqueId() {
		counter++;
		return "elem_" + Long.toString(counter);
	}

}
