package com.octo.gwt.test.csv;

import com.octo.gwt.test.AbstractGwtTestRunner;

public class GwtCsvTestRunner extends AbstractGwtTestRunner {

	private static final String classRunnerName = "com.octo.gwt.test.csv.internal.BlockJUnit4CsvRunner";

	public GwtCsvTestRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}
