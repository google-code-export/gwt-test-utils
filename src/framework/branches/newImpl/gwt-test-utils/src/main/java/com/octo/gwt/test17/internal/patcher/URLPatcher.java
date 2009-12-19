package com.octo.gwt.test17.internal.patcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javassist.CtMethod;

public class URLPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "encodeComponentImpl")) {
			return callMethod("encodeComponentImpl", "$1");
		}

		return null;
	}

	public static String encodeComponentImpl(String decodedURLComponent) {
		try {
			return URLEncoder.encode(decodedURLComponent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
