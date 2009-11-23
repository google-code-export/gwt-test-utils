package com.octo.gwt.test17.integ.tools;

import com.octo.gwt.test17.integ.csvrunner.CsvRunner;
import com.octo.gwt.test17.integ.csvrunner.Node;

public interface PrefixProcessor {

	Object process(CsvRunner csvRunner, Node next, boolean failOnError);

}
