package com.octo.gwt.test.internal.patcher;

import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class LocationPatcher extends AutomaticPatcher {
	
	@PatchMethod
	public static String getProtocol() {
		return "http";
	}
	
	@PatchMethod
	public static String getPort() {
		return "80";
	}

}
