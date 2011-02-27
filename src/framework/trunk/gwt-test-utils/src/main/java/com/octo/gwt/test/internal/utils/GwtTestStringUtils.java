package com.octo.gwt.test.internal.utils;

public abstract class GwtTestStringUtils {

	public static String camelize(String s) {
		String[] strings = s.split("[-|_|\\s]");

		if (strings.length <= 0)
			return "";

		StringBuilder sb = new StringBuilder(strings[0].toLowerCase());

		for (int i = 1; i < strings.length; i++) {
			String string = strings[i];
			if (string.length() > 0)
				sb.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1).toLowerCase());
		}

		return sb.toString();
	}

	public static String hyphenize(String string) {
		StringBuilder sb = new StringBuilder(string);

		for (int c = 0; c < sb.length(); c++) {
			char character = sb.charAt(c);
			if (Character.isUpperCase(character)) {
				sb.setCharAt(c, Character.toLowerCase(character));
				sb.insert(c, '-');
				c++;
			}
		}

		return sb.toString();
	}

	public static String dehyphenize(String string) {
		StringBuilder buffer = new StringBuilder(string);

		for (int c = 0; c < buffer.length(); c++) {
			char character = buffer.charAt(c);
			if (character == '-') {
				buffer.deleteCharAt(c);
				character = buffer.charAt(c);
				buffer.setCharAt(c, Character.toUpperCase(character));
			}
		}

		return buffer.toString();
	}

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
