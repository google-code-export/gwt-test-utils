package com.octo.gwt.test17.internal.patcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class URLPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String encodeComponentImpl(String decodedURLComponent) {
		try {
			return URLEncoder.encode(decodedURLComponent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
