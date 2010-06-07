package com.octo.gwt.test.internal.patcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.gwt.http.client.URL;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(URL.class)
public class URLPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String encodeComponentImpl(String decodedURLComponent) {
		try {
			return URLEncoder.encode(decodedURLComponent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
