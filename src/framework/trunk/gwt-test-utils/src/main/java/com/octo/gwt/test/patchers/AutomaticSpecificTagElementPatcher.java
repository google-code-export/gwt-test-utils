package com.octo.gwt.test.patchers;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.ModElement;
import com.google.gwt.dom.client.QuoteElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.octo.gwt.test.internal.GwtClassPool;
import com.octo.gwt.test.internal.utils.TagAware;

/**
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
@PatchClass({
    HeadingElement.class, ModElement.class, QuoteElement.class,
    TableColElement.class, TableSectionElement.class})
public class AutomaticSpecificTagElementPatcher extends OverlayPatcher {

  private static final String OVERRIDE_TAG = "overrideTag";

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);

    ClassPool cp = GwtClassPool.get();

    c.addInterface(cp.get(TagAware.class.getName()));

    CtField tagField = new CtField(cp.get(String.class.getName()),
        OVERRIDE_TAG, c);
    c.addField(tagField);

    CtConstructor constructor = new CtConstructor(
        new CtClass[]{cp.get(String.class.getName())}, c);
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("super();");
    sb.append("this." + OVERRIDE_TAG + " = $1;");
    sb.append("}");
    constructor.setBody(sb.toString());
    c.addConstructor(constructor);

    CtMethod getTag = new CtMethod(cp.get(String.class.getName()), "getTag",
        new CtClass[]{}, c);
    getTag.setBody("return " + OVERRIDE_TAG + ";");
    c.addMethod(getTag);
  }

}
