package com.googlecode.gwt.test.internal;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;

import com.googlecode.gwt.test.internal.MyClassToPatch.MyInnerClass;
import com.googlecode.gwt.test.internal.MyClassToPatchOverridePatcher.MyInnerClassOverridePatcher;
import com.googlecode.gwt.test.patchers.InitMethod;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

@PatchClass(MyClassToPatch.class)
public class MyClassToPatchPatcher {

  @PatchClass(MyInnerClass.class)
  public static class MyInnerClassPatcher {

    @PatchMethod
    public static String getInnerString(MyInnerClass innerObject) {
      String value = GwtReflectionUtils.getPrivateFieldValue(innerObject,
          "new_string_attr");
      return "patched by " + MyInnerClassOverridePatcher.class.getSimpleName()
          + " : " + value;
    }

    @InitMethod
    public static void initMyInnerClass(CtClass c)
        throws CannotCompileException {
      CtField field = CtField.make(
          "private String new_string_attr = \"new field added in init\";", c);
      c.addField(field);
    }
  }

  @PatchMethod
  public static String myStringMethod(MyClassToPatch myClassToPatch,
      MyInnerClass innerObject) {
    return "myStringMethod has been patched : " + innerObject.getInnerString();
  }

}
