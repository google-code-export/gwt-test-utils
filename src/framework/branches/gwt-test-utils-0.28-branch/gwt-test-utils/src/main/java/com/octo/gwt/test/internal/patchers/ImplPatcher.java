package com.octo.gwt.test.internal.patchers;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.gwt.core.client.impl.Impl;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Impl.class)
public class ImplPatcher {

  @PatchMethod
  public static int getHashCode(Object o) {
    return HashCodeBuilder.reflectionHashCode(o);
  }

  @PatchMethod
  public static String getHostPageBaseURL() {
    return "http://127.0.0.1:8888/";
  }

  @PatchMethod
  public static String getModuleBaseURL() {
    return getHostPageBaseURL() + getModuleName() + "/";
  }

  @PatchMethod
  public static String getModuleName() {
    return GwtConfig.get().getModuleName();
  }

}
