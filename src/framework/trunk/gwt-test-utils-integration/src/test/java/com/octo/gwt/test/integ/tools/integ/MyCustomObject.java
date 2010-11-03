package com.octo.gwt.test.integ.tools.integ;

import java.io.Serializable;

public class MyCustomObject implements Serializable {

	private static final long serialVersionUID = -4047482702956475369L;

	public String myField;
	public transient String myTransientField = "transient field";

	/**
	 * Default constructor for serialization
	 */
	protected MyCustomObject() {

	}

	public MyCustomObject(String myField) {
		this.myField = myField;
	}

}
