package com.octo.gwt.test.integ.tools;

import com.octo.gwt.test.integ.csvrunner.CsvRunner;

public interface ObjectFinder {

	Object find(CsvRunner csvRunner, String... params);

}
