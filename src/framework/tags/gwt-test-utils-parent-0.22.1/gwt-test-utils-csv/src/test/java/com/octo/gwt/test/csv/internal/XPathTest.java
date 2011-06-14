package com.octo.gwt.test.csv.internal;

import junit.framework.Assert;

import org.junit.Test;

import com.octo.gwt.test.csv.runner.Node;

public class XPathTest {

  @Test
  public void testA() {
    Assert.assertEquals("/toto", processString("/toto").toString());
    Assert.assertEquals("/t", processString("/t").toString());
  }

  @Test
  public void testB() {
    Assert.assertEquals("/toto/tata/tete/titi",
        processString("/toto/tata/tete/titi").toString());
    Assert.assertEquals("/t(a,)", processString("/t(a)").toString());
  }

  @Test
  public void testC() {
    Assert.assertEquals("/a(b ,)", processString("/a(b )").toString());
  }

  @Test
  public void testD() {
    Assert.assertEquals("/toto(aa bb,zz,)/titi/toto",
        processString("/toto(aa bb,zz)/titi/toto").toString());
  }

  @Test
  public void testE() {
    Assert.assertEquals("/toto/tata{zyy}",
        processString("/toto/tata[zyy]").toString());
  }

  @Test
  public void testErrorA() {
    Assert.assertNull(processString("/toto("));
  }

  @Test
  public void testErrorB() {
    Assert.assertNull(processString("/toto(a,)"));
  }

  @Test
  public void testErrorC() {
    Assert.assertNull(processString("/toto/"));
  }

  @Test
  public void testErrorD() {
    Assert.assertNull(processString("/toto//"));
  }

  @Test
  public void testF() {
    Assert.assertEquals("/toto/tata{zz yy}",
        processString("/toto/tata[zz yy]").toString());
  }

  @Test
  public void testG() {
    Assert.assertEquals("/toto/tata[/aa=zz yy]",
        processString("/toto/tata[aa=zz yy]").toString());
  }

  @Test
  public void testH() {
    Assert.assertEquals("/toto/tata[/aa/zz(b,)/toto=zz yy]",
        processString("/toto/tata[aa/zz(b)/toto=zz yy]").toString());
  }

  // @Test
  // public void testErrorA() {
  // Assert.assertNull(processString("toto"));
  // }

  @Test
  public void testI() {
    Assert.assertEquals("/toto/tata[/aa/zz[/b=2]/toto=zz yy]",
        processString("/toto/tata[aa/zz[b=2]/toto=zz yy]").toString());
  }

  @Test
  public void testInteg() {
    Assert.assertNotNull(processString("/view/paymentView/nextValidationButton"));
    Assert.assertNotNull(processString("/view/contractChooserPanel/stackPanel/widget(0)/contractTypesAnchors[OC00000002048]"));
    Assert.assertNotNull(processString("/view/configuratorStackPanel/widget[title=TV - 1 erreurs]"));
    Assert.assertNotNull(processString("/view/configuratorStackPanel/widget[title=TV - 1 erreur(s)]"));
    Assert.assertNotNull(processString("/view/configuratorStackPanel/widget[title=TV - 1 erreur(s)]/widget[widget(0)/text=Décodeur HauteDef Enregistreur]/widget(1)"));
    Assert.assertNotNull(processString("/view/configurationGrid/parametersGrid/widgetMap/widgetList[text=portal.contrats.OC00000002048]"));
  }

  @Test
  public void testJ() {
    Assert.assertNull(processString("/_toto"));
    Assert.assertNotNull(processString("/toto"));
  }

  @Test
  public void testK() {
    Assert.assertNull(processString("/à"));
    Assert.assertNotNull(processString("/toto(à)"));
    Assert.assertEquals("/toto(àéèê,)", processString("/toto(àéèê)").toString());
  }

  private Node processString(String s) {
    Node res = Node.parse(s);
    return res;
  }

}
