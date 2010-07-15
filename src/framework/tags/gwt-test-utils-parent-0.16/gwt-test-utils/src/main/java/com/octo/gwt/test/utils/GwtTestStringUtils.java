package com.octo.gwt.test.utils;

public class GwtTestStringUtils {

	public static String resolveBackSlash(String input) {
		if (input == null || "".equals(input.trim()))
			return input;

		StringBuffer b = new StringBuffer();
		boolean backSlashSeen = false;
		for (int i = 0; i < input.length(); ++i) {
			char c = input.charAt(i);
			if (!backSlashSeen) {
				if (c == '\\') {
					backSlashSeen = true;
				} else {
					b.append(c);
				}
			} else {
				switch (c) {
				case '\\':
					b.append((char) '\\');
					break;
				case 'n':
					b.append((char) '\n');
					break;
				case 'r':
					b.append((char) '\r');
					break;
				case 't':
					b.append((char) '\t');
					break;
				case 'f':
					b.append((char) '\f');
					break;
				case 'b':
					b.append((char) '\b');
					break;
				default:
					b.append(c);
				}
				backSlashSeen = false;
			}
		}
		return b.toString();
	}
}
