package com.octo.gwt.test.integ.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.apache.commons.lang.SerializationUtils;

public class TestExter {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(TestEx.class.getMethod("writeExternal", ObjectOutput.class).getModifiers());

		TestEx t1 = new TestEx(1);
		t1.trans = "modified";
		t1.parentInt = 2;
		t1.child = new TestEx(6);
		t1.child.parent = t1;

		TestEx t2 = (TestEx) SerializationUtils.clone(t1);

		System.out.println(t2.myInt);
		System.out.println(t2.trans);
		System.out.println(t2.parentInt);
		System.out.println(t2.child.myInt);
		System.out.println(t2.child.trans);
		System.out.println(t2.child.parent.myInt);

	}

	public static class TestEx extends Parent implements Externalizable {

		private int myInt;
		private transient String trans = "transient string reinit !";
		private TestEx child;
		private TestEx parent;

		public TestEx() {

		}

		public TestEx(int myInt) {
			this.myInt = myInt;
		}

		public void writeExternal(ObjectOutput out) throws IOException {
			ExternalizableModifier.writeExternal(TestEx.this, out);

		}

		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			ExternalizableModifier.readExternal(TestEx.this, in);

		}

	}

	public static class Parent {

		protected int parentInt = 4;

	}

}
