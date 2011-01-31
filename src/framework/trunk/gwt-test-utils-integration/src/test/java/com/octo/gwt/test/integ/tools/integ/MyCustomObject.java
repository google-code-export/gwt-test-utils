package com.octo.gwt.test.integ.tools.integ;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MyCustomObject implements IsSerializable {

	private static final long serialVersionUID = -4047482702956475369L;

	public String myField;
	public transient String myTransientField = "transient field";

	/**
	 * Default constructor for serialization
	 */
	MyCustomObject() {

	}

	public MyCustomObject(String myField) {
		this.myField = myField;
	}

}
