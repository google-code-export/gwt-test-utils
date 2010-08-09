package com.octo.gwt.test.integ.tools;

import com.octo.gwt.test.integ.csvrunner.CsvRunner;

public interface ObjectFinder {

	boolean accept(String... params);

	Object find(CsvRunner csvRunner, String... params);

}
