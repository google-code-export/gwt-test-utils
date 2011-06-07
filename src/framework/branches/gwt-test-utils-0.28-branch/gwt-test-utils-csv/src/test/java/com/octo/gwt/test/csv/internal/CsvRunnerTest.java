package com.octo.gwt.test.csv.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.octo.gwt.test.csv.CsvMethod;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.CsvRunnerException;
import com.octo.gwt.test.csv.runner.Node;

public class CsvRunnerTest {

  class A {

    public String zzz = "zzz";

    @SuppressWarnings("unused")
    private String zz = "zz";

    @CsvMethod
    public Object getMe() {
      return this;
    }

    @CsvMethod
    public String getPublic() {
      return "public";
    }

    @SuppressWarnings("unused")
    private String getPrivate() {
      return "private";
    }

    @CsvMethod
    Map<String, String> getMap() {
      Map<String, String> map = new HashMap<String, String>();
      map.put("a", "b");
      map.put("c", "d");
      return map;
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
    void methArray1(String a, String[] p) {
      Assert.assertEquals("a", a);
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
  }

  class B extends A {

  }

  class MyException extends Exception {

    private static final long serialVersionUID = 1L;

  }

  class SimiliWidget {

    private String id;

    private String label;

    private List<SimiliWidget> list;

    public SimiliWidget(String id, String label) {
      this.list = new ArrayList<SimiliWidget>();
      this.id = id;
      this.label = label;
    }

    public List<SimiliWidget> getCurrentList() {
      return list;
    }

    public String getLabel() {
      return label;
    }

    public String getLabelWithParam(String s) {
      return label;
    }

    public SimiliWidget getWidget(int index) {
      return list.get(index);
    }

    public int getWidgetCount() {
      return list.size();
    }

    public void setLabel(String label) {
      this.label = label;
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

  };

  private Object o = new A();

  private Object oo = new B();
  private CsvRunner runner = new CsvRunner();

  @Test(expected = CsvRunnerException.class)
  public void checkException() throws CsvRunnerException {
    runner.executeLine("runMyException", new ArrayList<String>(), o);
  }

  @Test
  public void checkGetInList() {
    SimiliWidgetContainer root = getList();
    Assert.assertTrue(root.getWidget().list.get(2) == runner.getNodeValue(root,
        Node.parse("/widget/widget(2)")));
    Assert.assertTrue(root.getWidget().list.get(2) == runner.getNodeValue(root,
        Node.parse("/widget/widget[label=child3]")));
    Assert.assertTrue(root.getWidget().list.get(2) == runner.getNodeValue(root,
        Node.parse("/widget/widget[getLabel=child3]")));
    Assert.assertTrue(root.getWidget().list.get(2) == runner.getNodeValue(root,
        Node.parse("/widget/widget[id=child3Id]")));
    Assert.assertTrue(root.getWidget().list.get(2) == runner.getNodeValue(root,
        Node.parse("/widget/list[id=child3Id]")));
    Assert.assertTrue(root.getWidget().list.get(2) == runner.getNodeValue(root,
        Node.parse("/widget/getCurrentlist[id=child3Id]")));
  }

  @Test
  public void checkGetInListRecurse() {
    SimiliWidgetContainer root = getList();
    Assert.assertTrue(root.getWidget().list.get(2) == runner.getNodeValue(root,
        Node.parse("/widget/widget[label/toString=child3]")));
    Assert.assertTrue(root.getWidget().list.get(2) == runner.getNodeValue(root,
        Node.parse("/widget/widget[labelWithParam(a)/toString=child3]")));
    Assert.assertTrue(root.getWidget().list.get(2).id == runner.getNodeValue(
        root, Node.parse("/widget/widget[label/toString=child3]/id")));
  }

  @Test
  public void checkGetMap() {
    Assert.assertEquals("b", runner.getNodeValue(o, Node.parse("/map[a]")));
    Assert.assertEquals("d", runner.getNodeValue(o, Node.parse("/map[c]")));
  }

  @Test
  public void checkGetMapNotFound() {
    Assert.assertNull(runner.getNodeValue(o, Node.parse("/map[b]")));
  }

  @Test
  public void checkGetter() {
    Assert.assertEquals("public", runner.getNodeValue(o, Node.parse("/public")));
    Assert.assertEquals("public",
        runner.getNodeValue(o, Node.parse("/getpublic")));
    Assert.assertEquals("private",
        runner.getNodeValue(o, Node.parse("/private")));
    Assert.assertEquals("zz", runner.getNodeValue(o, Node.parse("/zz")));
    Assert.assertEquals("zzz", runner.getNodeValue(o, Node.parse("/zzz")));
    Assert.assertEquals("zzz",
        runner.getNodeValue(o, Node.parse("/me/ME/getMe/zzz")));
    Assert.assertNotNull("zz", runner.getNodeValue(o, Node.parse("/toString")));
  }

  @Test
  public void checkGetterDerived() {
    Assert.assertEquals("zz", runner.getNodeValue(oo, Node.parse("/zz")));
    Assert.assertEquals("zzz", runner.getNodeValue(oo, Node.parse("/zzz")));
  }

  @Test
  public void checkGetWidget() {
    Assert.assertEquals("toto",
        runner.getNodeValue(o, Node.parse("/getWidget(toto)")));
    Assert.assertEquals("toto",
        runner.getNodeValue(o, Node.parse("/WIDGET(toto)")));
  }

  @Test
  public void checkGetWidgetInt() {
    Assert.assertEquals("0",
        runner.getNodeValue(o, Node.parse("/getWidgetInt(0)")));
    Assert.assertEquals("12",
        runner.getNodeValue(o, Node.parse("/getWidgetInt(12)")));
  }

  @Test
  public void checkMeth0() throws Exception {
    runner.executeLine("meth0", new ArrayList<String>(), o);
  }

  @Test
  public void checkMeth0Derived() throws Exception {
    runner.executeLine("meth0", new ArrayList<String>(), oo);
  }

  @Test
  public void checkMeth1() throws Exception {
    runner.executeLine("meth1", Arrays.asList("p0"), o);
  }

  @Test(expected = AssertionError.class)
  public void checkMeth1WrongValue() throws CsvRunnerException {
    runner.executeLine("meth1", Arrays.asList("p4"), o);
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
  public void checkMethArray1() throws Exception {
    runner.executeLine("methArray1", Arrays.asList("a", "p0", "p1", "p2"), o);
  }

  @Test
  public void checkMethVar0() throws Exception {
    runner.executeLine("methVar0", Arrays.asList("p0", "p1", "p2"), o);
  }

  @Test
  public void checkMethVar1() throws Exception {
    runner.executeLine("methVar1", Arrays.asList("a", "p0", "p1", "p2"), o);
  }

  @Test(expected = CsvRunnerException.class)
  public void checkRuntime() throws CsvRunnerException {
    runner.executeLine("runException", new ArrayList<String>(), o);
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

}
