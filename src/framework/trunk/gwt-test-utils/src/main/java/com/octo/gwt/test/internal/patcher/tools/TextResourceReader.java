package com.octo.gwt.test.internal.patcher.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TextResourceReader {

	private static Map<String, String> cache;

	public static String readFile(File file) throws UnsupportedEncodingException, IOException {
		if (cache == null) {
			cache = new HashMap<String, String>();
		}

		if (!cache.containsKey(file.getName())) {
			StringBuilder sb = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\r\n");
			}
			
			if (sb.length() > 0) {
				sb.delete(sb.length() - "\r\n".length(), sb.length());
			}
			
			cache.put(file.getName(), sb.toString());
		}

		return cache.get(file.getName());
	}

	public static void reset() {
		cache.clear();
	}

}
