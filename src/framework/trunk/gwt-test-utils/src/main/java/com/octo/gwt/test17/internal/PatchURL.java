package com.octo.gwt.test17.internal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PatchURL {
	
	public static String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
