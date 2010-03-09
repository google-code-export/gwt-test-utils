package com.octo.gwt.test.integ.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class MacroReader {

	private final static Logger logger = Logger.getLogger(MacroReader.class);

	private HashMap<String, List<List<String>>> macroList;

	public MacroReader() {
		macroList = new HashMap<String, List<List<String>>>();
	}

	public List<List<String>> getMacro(String macroName) {
		return macroList.get(macroName);
	}

	public void read(List<List<String>> sheet) {
		logger.info("Start reading macro");
		String currentMacroName = null;
		List<List<String>> currentMacroContent = null;
		for (List<String> row : sheet) {
			if (currentMacroName != null) {
				if (row.size() > 0 && "endmacro".equals(row.get(0))) {
					logger.info("End macro, length : " + currentMacroContent.size());
					macroList.put(currentMacroName, currentMacroContent);
					currentMacroName = null;
				} else {
					currentMacroContent.add(row);
				}
			} else {
				if (row.size() > 0 && "macro".equals(row.get(0))) {
					currentMacroName = row.size() > 1 ? row.get(1) : null;
					Assert.assertNotNull("You have to specified a macro name", currentMacroName);
					logger.info("Starting reading " + currentMacroName);
					currentMacroContent = new ArrayList<List<String>>();
				}
			}
		}
	}

}