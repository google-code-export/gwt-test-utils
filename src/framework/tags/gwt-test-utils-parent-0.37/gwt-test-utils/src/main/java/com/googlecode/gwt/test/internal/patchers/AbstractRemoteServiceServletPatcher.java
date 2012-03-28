package com.googlecode.gwt.test.internal.patchers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;

import com.google.gwt.user.server.rpc.AbstractRemoteServiceServlet;
import com.googlecode.gwt.test.internal.GwtConfig;
import com.googlecode.gwt.test.patchers.InitMethod;
import com.googlecode.gwt.test.patchers.PatchClass;

@PatchClass(AbstractRemoteServiceServlet.class)
class AbstractRemoteServiceServletPatcher {

  @InitMethod
  static void addGetServletConfigMethod(CtClass c)
      throws CannotCompileException {

    StringBuilder sb = new StringBuilder();
    sb.append("public javax.servlet.ServletConfig getServletConfig() { return ");
    sb.append(GwtConfig.class.getName()).append(".get().getServletConfig(); }");

    CtMethod m = CtMethod.make(sb.toString(), c);
    c.addMethod(m);
  }

}
