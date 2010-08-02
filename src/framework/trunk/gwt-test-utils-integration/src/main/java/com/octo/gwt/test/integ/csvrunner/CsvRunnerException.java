package com.octo.gwt.test.integ.csvrunner;

public class CsvRunnerException extends Exception {

	private static final long serialVersionUID = -3924589163556048374L;

	public CsvRunnerException(CsvRunner runner, Throwable cause) {
		super(runner.getAssertionErrorMessagePrefix(), cause);
	}

}
