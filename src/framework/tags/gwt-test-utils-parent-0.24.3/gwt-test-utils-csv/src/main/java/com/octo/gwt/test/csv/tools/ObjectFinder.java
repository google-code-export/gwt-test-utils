package com.octo.gwt.test.csv.tools;

import com.octo.gwt.test.csv.runner.CsvRunner;

public interface ObjectFinder {

	boolean accept(String... params);

	Object find(CsvRunner csvRunner, String... params);

}
