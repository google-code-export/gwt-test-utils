package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Window.Location;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Location.class)
public class LocationPatcher {

	@PatchMethod
	public static String getProtocol() {
		return "http";
	}

	@PatchMethod
	public static String getPort() {
		return "80";
	}

}
