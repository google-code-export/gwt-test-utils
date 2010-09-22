package com.octo.gwt.test.integ.tools;

import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.integ.csvrunner.Node;

public interface NodeObjectFinder {

	Object find(CsvRunner csvRunner, Node node);
}
