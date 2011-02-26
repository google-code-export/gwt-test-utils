package com.octo.gwt.test.integration;

import java.io.Serializable;

public class MyChildObject implements Serializable {

	private static final long serialVersionUID = -8359127151374995805L;

	public String myChildField;
	public transient String myChildTransientField = "child object transient field";

	/**
	 * Default constructor for serialization
	 */
	MyChildObject() {

	}

	public MyChildObject(String myChildField) {
		this.myChildField = myChildField;
	}

}
