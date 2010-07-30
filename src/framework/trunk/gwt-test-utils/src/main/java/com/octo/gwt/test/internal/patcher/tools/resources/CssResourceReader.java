package com.octo.gwt.test.internal.patcher.tools.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CssResourceReader {

	private static Map<String, CssParsingResult> cache;

	public static CssParsingResult readFile(File file) throws UnsupportedEncodingException, IOException {
		if (cache == null) {
			cache = new HashMap<String, CssParsingResult>();
		}

		if (!cache.containsKey(file.getName())) {

			Map<String, String> constants = new HashMap<String, String>();
			Set<String> styles = new HashSet<String>();

			Pattern constantPattern = Pattern.compile("^\\s*@def (\\S+)\\s+(\\S+)\\s*$");

			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

				String line;
				Matcher m;
				while ((line = reader.readLine()) != null) {
					m = constantPattern.matcher(line);
					if (m.matches()) {
						constants.put(m.group(1), m.group(2));
					}

				}

				cache.put(file.getName(), new CssParsingResult(constants, styles));

			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}

		return cache.get(file.getName());
	}

	public static void reset() {
		cache.clear();
	}

	public static class CssParsingResult {

		private Map<String, String> constants;

		private CssParsingResult(Map<String, String> constants, Set<String> styles) {
			this.constants = constants;
		}

		public Map<String, String> getConstants() {
			return constants;
		}
	}

}
