package com.octo.gwt.test.internal.patchers;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.internal.GwtClassPool;
import com.octo.gwt.test.internal.overrides.OverrideJsArrayString;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerAware;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JavaScriptObject.class)
public class JavaScriptObjectPatcher extends AutomaticPatcher {

  private static final String PROPERTIES = "__PROPERTIES__";

  @PatchMethod
  public static JavaScriptObject createArray() {
    return new OverrideJsArrayString();
  }

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);

    // add field "protected PropertyContainer __PROPERTIES__;"
    CtClass pcType = GwtClassPool.getCtClass(PropertyContainer.class);
    CtField propertiesField = CtField.make("protected final "
        + PropertyContainer.class.getName() + " " + PROPERTIES + " = "
        + PropertyContainerUtils.getConstructionCode() + ";", c);
    c.addField(propertiesField);

    c.addInterface(GwtClassPool.getCtClass(PropertyContainerAware.class));

    CtMethod getProperties = new CtMethod(pcType, "getProperties",
        new CtClass[]{}, c);
    getProperties.setBody("return " + PROPERTIES + ";");
    c.addMethod(getProperties);
  }

}
