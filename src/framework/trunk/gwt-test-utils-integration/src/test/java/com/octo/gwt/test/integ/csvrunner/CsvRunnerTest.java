package com.octo.gwt.test.integ.csvrunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.octo.gwt.test.integ.CsvMethod;

public class CsvRunnerTest {

	private CsvRunner runner = new CsvRunner();

	class MyException extends Exception {

		private static final long serialVersionUID = 1L;

	}

	class SimiliWidget {

		private List<SimiliWidget> list;

		private String label;

		private String id;

		public SimiliWidget(String id, String label) {
			this.list = new ArrayList<SimiliWidget>();
			this.id = id;
			this.label = label;
		}

		public SimiliWidget getWidget(int index) {
			return list.get(index);
		}

		public int getWidgetCount() {
			return list.size();
		}

		public String getLabel() {
			return label;
		}

		public String getLabelWithParam(String s) {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public List<SimiliWidget> getCurrentList() {
			return list;
		}

	}

	class SimiliWidgetContainer {

		private SimiliWidget widget;

		public SimiliWidgetContainer(SimiliWidget widget) {
			this.widget = widget;
		}

		public SimiliWidget getWidget() {
			return widget;
		}

	}

	class A {

		@CsvMethod
		public String getPublic() {
			return "public";
		}

		@CsvMethod
		public Object getMe() {
			return this;
		}

		@SuppressWarnings("unused")
		private String getPrivate() {
			return "private";
		}

		public String zzz = "zzz";

		@SuppressWarnings("unused")
		private String zz = "zz";

		@CsvMethod
		void meth0() {
			Assert.assertTrue(true);
		}

		@CsvMethod
		void meth1(String p0) {
			Assert.assertEquals("p0", p0);
		}

		@CsvMethod
		void meth2(String p0, String p1) {
			Assert.assertEquals("p0", p0);
			Assert.assertEquals("p1", p1);
		}

		@CsvMethod
		void meth3(String p0, String p1, String p2) {
			Assert.assertEquals("p0", p0);
			Assert.assertEquals("p1", p1);
			Assert.assertEquals("p2", p2);
		}

		@CsvMethod
		void methArray0(String[] p) {
			Assert.assertEquals(3, p.length);
			Assert.assertEquals("p0", p[0]);
			Assert.assertEquals("p1", p[1]);
			Assert.assertEquals("p2", p[2]);

		}

		@CsvMethod
		void methVar0(String... p) {
			Assert.assertEquals(3, p.length);
			Assert.assertEquals("p0", p[0]);
			Assert.assertEquals("p1", p[1]);
			Assert.assertEquals("p2", p[2]);
		}

		@CsvMethod
		void methArray1(String a, String[] p) {
			Assert.assertEquals("a", a);
			Assert.assertEquals(3, p.length);
			Assert.assertEquals("p0", p[0]);
			Assert.assertEquals("p1", p[1]);
			Assert.assertEquals("p2", p[2]);
		}

		@CsvMethod
		void methVar1(String a, String... p) {
			Assert.assertEquals("a", a);
			Assert.assertEquals(3, p.length);
			Assert.assertEquals("p0", p[0]);
			Assert.assertEquals("p1", p[1]);
			Assert.assertEquals("p2", p[2]);
		}

		@CsvMethod
		void runException() {
			throw new UnsupportedOperationException();
		}

		@CsvMethod
		void runMyException() throws MyException {
			throw new MyException();
		}

		@CsvMethod
		String getWidget(String i) {
			return i;
		}

		@CsvMethod
		String getWidgetInt(int i) {
			return Integer.toString(i);
		}

		@CsvMethod
		Map<String, String> getMap() {
			Map<String, String> map = new HashMap<String, String>();
			map.put("a", "b");
			map.put("c", "d");
			return map;
		}
	};

	class B extends A {

	}

	private Object o = new A();
	private Object oo = new B();

	@Test
	public void checkMeth0() throws Exception {
		runner.executeLine("meth0", new ArrayList<String>(), o);
	}

	@Test
	public void checkMeth1() throws Exception {
		runner.executeLine("meth1", Arrays.asList("p0"), o);
	}

	@Test
	public void checkMeth2() throws Exception {
		runner.executeLine("meth2", Arrays.asList("p0", "p1"), o);
	}

	@Test
	public void checkMeth3() throws Exception {
		runner.executeLine("meth3", Arrays.asList("p0", "p1", "p2"), o);
	}

	@Test
	public void checkMethArray0() throws Exception {
		runner.executeLine("methArray0", Arrays.asList("p0", "p1", "p2"), o);
	}

	@Test
	public void checkMethVar0() throws Exception {
		runner.executeLine("methVar0", Arrays.asList("p0", "p1", "p2"), o);
	}

	@Test
	public void checkMethArray1() throws Exception {
		runner.executeLine("methArray1", Arrays.asList("a", "p0", "p1", "p2"), o);
	}

	@Test
	public void checkMethVar1() throws Exception {
		runner.executeLine("methVar1", Arrays.asList("a", "p0", "p1", "p2"), o);
	}

	@Test(expected = CsvRunnerException.class)
	public void checkMeth1WrongValue() throws Exception {
		runner.executeLine("meth1", Arrays.asList("p4"), o);
	}

	@Test(expected = CsvRunnerException.class)
	public void checkRuntime() throws Exception {
		runner.executeLine("runException", new ArrayList<String>(), o);
	}

	@Test(expected = CsvRunnerException.class)
	public void checkException() throws Exception {
		runner.executeLine("runMyException", new ArrayList<String>(), o);
	}

	@Test
	public void checkGetter() {
		Assert.assertEquals("public", runner.getValue(o, Node.parse("/public")));
		Assert.assertEquals("public", runner.getValue(o, Node.parse("/getpublic")));
		Assert.assertEquals("private", runner.getValue(o, Node.parse("/private")));
		Assert.assertEquals("zz", runner.getValue(o, Node.parse("/zz")));
		Assert.assertEquals("zzz", runner.getValue(o, Node.parse("/zzz")));
		Assert.assertEquals("zzz", runner.getValue(o, Node.parse("/me/ME/getMe/zzz")));
		Assert.assertNotNull("zz", runner.getValue(o, Node.parse("/toString")));
	}

	@Test
	public void checkGetWidget() {
		Assert.assertEquals("toto", runner.getValue(o, Node.parse("/getWidget(toto)")));
		Assert.assertEquals("toto", runner.getValue(o, Node.parse("/WIDGET(toto)")));
	}

	@Test
	public void checkGetWidgetInt() {
		Assert.assertEquals("0", runner.getValue(o, Node.parse("/getWidgetInt(0)")));
		Assert.assertEquals("12", runner.getValue(o, Node.parse("/getWidgetInt(12)")));
	}

	@Test
	public void checkGetMap() {
		Assert.assertEquals("b", runner.getValue(o, Node.parse("/map[a]")));
		Assert.assertEquals("d", runner.getValue(o, Node.parse("/map[c]")));
	}

	@Test(expected = AssertionError.class)
	public void checkGetMapNotFound() {
		Assert.assertNull(runner.getValue(o, Node.parse("/map[b]")));
	}

	@Test
	public void checkMeth0Derived() throws Exception {
		runner.executeLine("meth0", new ArrayList<String>(), oo);
	}

	@Test
	public void checkGetterDerived() {
		Assert.assertEquals("zz", runner.getValue(oo, Node.parse("/zz")));
		Assert.assertEquals("zzz", runner.getValue(oo, Node.parse("/zzz")));
	}

	private SimiliWidgetContainer getList() {
		SimiliWidget root = new SimiliWidget("rootId", "root");
		SimiliWidget child1 = new SimiliWidget("child1Id", "child1");
		SimiliWidget child2 = new SimiliWidget("child2Id", "child2");
		SimiliWidget child3 = new SimiliWidget("child3Id", "child3");
		root.list.add(child1);
		root.list.add(child2);
		root.list.add(child3);
		return new SimiliWidgetContainer(root);
	}

	@Test
	public void checkGetInList() {
		SimiliWidgetContainer root = getList();
		Assert.assertTrue(root.getWidget().list.get(2) == runner.getValue(root, Node.parse("/widget/widget(2)")));
		Assert.assertTrue(root.getWidget().list.get(2) == runner.getValue(root, Node.parse("/widget/widget[label=child3]")));
		Assert.assertTrue(root.getWidget().list.get(2) == runner.getValue(root, Node.parse("/widget/widget[getLabel=child3]")));
		Assert.assertTrue(root.getWidget().list.get(2) == runner.getValue(root, Node.parse("/widget/widget[id=child3Id]")));
		Assert.assertTrue(root.getWidget().list.get(2) == runner.getValue(root, Node.parse("/widget/list[id=child3Id]")));
		Assert.assertTrue(root.getWidget().list.get(2) == runner.getValue(root, Node.parse("/widget/getCurrentlist[id=child3Id]")));
	}

	@Test
	public void checkGetInListRecurse() {
		SimiliWidgetContainer root = getList();
		Assert.assertTrue(root.getWidget().list.get(2) == runner.getValue(root, Node.parse("/widget/widget[label/toString=child3]")));
		Assert.assertTrue(root.getWidget().list.get(2) == runner.getValue(root, Node.parse("/widget/widget[labelWithParam(a)/toString=child3]")));
		Assert.assertTrue(root.getWidget().list.get(2).id == runner.getValue(root, Node.parse("/widget/widget[label/toString=child3]/id")));
	}

}
