package com.octo.gwt.test.csv;

import com.octo.gwt.test.GwtRunnerBase;

public class GwtCsvRunner extends GwtRunnerBase {

	private static final String classRunnerName = "com.octo.gwt.test.csv.internal.BlockJUnit4CsvRunner";

	public GwtCsvRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}
