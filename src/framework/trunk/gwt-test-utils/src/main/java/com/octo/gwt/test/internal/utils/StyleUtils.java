package com.octo.gwt.test.internal.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.patchers.dom.StylePatcher;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class StyleUtils {

	private static Pattern STYLE_PATTERN = Pattern.compile("(.+):(.+)");

	private StyleUtils() {

	}

	public static void setStyle(Style target, String value) {
		for (Map.Entry<String, String> entry : getStyleProperties(value).entrySet()) {
			target.setProperty(GwtStringUtils.camelize(entry.getKey()), entry.getValue());
		}
	}

	public static LinkedHashMap<String, String> getStyleProperties(String style) {
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();

		String[] styles = style.split("\\s*;\\s*");
		for (String property : styles) {
			Matcher m = STYLE_PATTERN.matcher(property);
			if (m.matches()) {
				result.put(m.group(1).trim(), m.group(2).trim());
			}
		}

		return result;
	}

	public static Element getOwnerElement(Style style) {
		return GwtReflectionUtils.getPrivateFieldValue(style, StylePatcher.TARGET_ELEMENT);
	}

}
