package com.octo.gwt.test.integration;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MyObject implements IsSerializable {

	private static final long serialVersionUID = -4047482702956475369L;

	public String myField;
	public transient String myTransientField = "transient field";
	public List<MyChildObject> myChildObjects = new ArrayList<MyChildObject>();

	/**
	 * Default constructor for serialization
	 */
	MyObject() {

	}

	public MyObject(String myField) {
		this.myField = myField;
	}

}
