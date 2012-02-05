package com.octo.gwt.test;

public abstract class GwtTestTest extends GwtTest {

	@Override
	public String getModuleName() {
		return "com.octo.gwt.test.GwtTestUtils";
	}

	@Override
	protected String getHostPagePath(String moduleFullQualifiedName) {
		return null;
	}

}
