package com.octo.gwt.test.internal.patcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.gwt.http.client.URL;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(URL.class)
public class URLPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String encodeQueryStringImpl(String decodedURLComponent) {
		try {
			return URLEncoder.encode(decodedURLComponent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
