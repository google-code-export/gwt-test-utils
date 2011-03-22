package com.octo.gwt.test.integration;

import java.io.Serializable;

public class MyChildObject extends MyObject implements Serializable {

	private static final long serialVersionUID = -8359127151374995805L;

	private String myChildField;
	private transient String myChildTransientField = "child object transient field";

	/**
	 * Default constructor for serialization
	 */
	MyChildObject() {

	}

	public MyChildObject(String myChildField) {
		this.myChildField = myChildField;
	}

	public String getMyChildField() {
		return myChildField;
	}

	public void setMyChildField(String myChildField) {
		this.myChildField = myChildField;
	}

	public String getMyChildTransientField() {
		return myChildTransientField;
	}

	public void setMyChildTransientField(String myChildTransientField) {
		this.myChildTransientField = myChildTransientField;
	}

}
