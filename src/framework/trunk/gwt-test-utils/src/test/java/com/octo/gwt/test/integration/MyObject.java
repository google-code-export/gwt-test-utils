package com.octo.gwt.test.integration;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MyObject implements IsSerializable {

	private static final long serialVersionUID = -4047482702956475369L;

	private String myField;
	private transient String myTransientField = "transient field";
	private List<MyChildObject> myChildObjects = new ArrayList<MyChildObject>();

	/**
	 * Default constructor for serialization
	 */
	MyObject() {

	}

	public MyObject(String myField) {
		this.myField = myField;
	}

	public String getMyField() {
		return myField;
	}

	public void setMyField(String myField) {
		this.myField = myField;
	}

	public String getMyTransientField() {
		return myTransientField;
	}

	public void setMyTransientField(String myTransientField) {
		this.myTransientField = myTransientField;
	}

	public List<MyChildObject> getMyChildObjects() {
		return myChildObjects;
	}

}
