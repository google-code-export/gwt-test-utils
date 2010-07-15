package com.octo.gwt.test.integ.tools;

import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.integ.csvrunner.Node;

public interface PrefixProcessor {

	Object process(CsvRunner csvRunner, Node next, boolean failOnError);

}
