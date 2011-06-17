package com.octo.gwt.test.patchers;

public class MyClassToPatch {

	public String myStringMethod(MyInnerClass innerObject) throws Exception {
		throw new Exception("Method myStringMethod has not been patched");
	}

	public static class MyInnerClass {

		private String innerString;

		public MyInnerClass(String innerString) {
			this.innerString = innerString;
		}

		public String getInnerString() {
			return innerString;
		}

	}

}
