package com.octo.gwt.test.internal.patcher;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Cookies;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(Cookies.class)
public class CookiesPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String uriEncode(String s) {
		return s;
	}

	@PatchMethod
	public static void setCookieImpl(String name, String value, double expires,
			String domain, String path, boolean secure) {

		Map<String, String> cachedCookies = getCookiesMap();
		if (cachedCookies == null) {
			cachedCookies = new HashMap<String, String>();
			GwtTestReflectionUtils.setStaticField(Cookies.class, "cachedCookies", cachedCookies);
		}
		
		cachedCookies.put(name, value);
	}
	
	@PatchMethod
	public static boolean needsRefresh() {
		return false;
	}
	
	@PatchMethod
	public static void removeCookieNative(String name) {
		Map<String, String> cachedCookies = getCookiesMap();
		cachedCookies.remove(name);
	}
	
	@PatchMethod
	public static void loadCookies(HashMap<String, String> m) {
	}
	
	private static Map<String, String> getCookiesMap() {
		Map<String, String> cachedCookies = GwtTestReflectionUtils.getStaticFieldValue(Cookies.class, "cachedCookies");
		return cachedCookies;
	}

}
