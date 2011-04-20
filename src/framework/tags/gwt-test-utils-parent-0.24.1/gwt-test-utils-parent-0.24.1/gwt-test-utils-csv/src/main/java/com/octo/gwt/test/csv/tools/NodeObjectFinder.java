package com.octo.gwt.test.csv.tools;

import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.Node;

public interface NodeObjectFinder {

	Object find(CsvRunner csvRunner, Node node);
}
