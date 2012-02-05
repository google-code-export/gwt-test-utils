package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.List;

//TODO : delete
public class PropertiesStringModifier {

	private static class SequenceReplacement {

		private final String regex;

		private final String to;

		public SequenceReplacement(String regex, String to) {
			this.regex = regex;
			this.to = to;
		}

		public String treat(String s) {
			return s.replaceAll(regex, to);
		}

	}

	private static final PropertiesStringModifier INSTANCE = new PropertiesStringModifier();

	public static PropertiesStringModifier get() {
		return INSTANCE;
	}

	private final List<SequenceReplacement> sequenceReplacements;

	private PropertiesStringModifier() {
		sequenceReplacements = new ArrayList<SequenceReplacement>();
		reset();
	}

	public void replaceSequence(String regex, String to) {
		sequenceReplacements.add(new SequenceReplacement(regex, to));
	}

	public void reset() {
		sequenceReplacements.clear();
		// hardcoded to fix gwt "bug"
		sequenceReplacements.add(new SequenceReplacement("\\u00A0", " "));
	}

	public String treatString(String string) {
		for (SequenceReplacement sequenceReplacement : sequenceReplacements) {
			string = sequenceReplacement.treat(string);
		}

		return string;
	}

}
