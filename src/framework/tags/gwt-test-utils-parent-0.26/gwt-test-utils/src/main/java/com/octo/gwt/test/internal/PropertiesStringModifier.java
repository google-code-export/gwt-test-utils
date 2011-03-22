package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.List;

public class PropertiesStringModifier {

	private static PropertiesStringModifier INSTANCE;

	public static PropertiesStringModifier get() {
		if (INSTANCE == null) {
			INSTANCE = new PropertiesStringModifier();
		}

		return INSTANCE;
	}

	private List<SequenceReplacement> sequenceReplacements;

	private PropertiesStringModifier() {
		sequenceReplacements = new ArrayList<SequenceReplacement>();
		reset();
	}

	public void replaceSequence(String regex, String to) {
		sequenceReplacements.add(new SequenceReplacement(regex, to));
	}

	public String treatString(String string) {
		for (SequenceReplacement sequenceReplacement : sequenceReplacements) {
			string = sequenceReplacement.treat(string);
		}

		return string;
	}

	public void reset() {
		sequenceReplacements.clear();
		// hardcoded to fix gwt "bug"
		sequenceReplacements.add(new SequenceReplacement("\\u00A0", " "));
	}

	private static class SequenceReplacement {

		private String regex;

		private String to;

		public String treat(String s) {
			return s.replaceAll(regex, to);
		}

		public SequenceReplacement(String regex, String to) {
			this.regex = regex;
			this.to = to;
		}

	}

}
