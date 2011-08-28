package com.octo.gwt.test.internal.handlers;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.internal.ModuleData;

public class DeferredReplaceWithCreateHandler implements GwtCreateHandler {

  public Object create(Class<?> classLiteral) throws Exception {

    String replaceWith = ModuleData.get().getReplaceWithClass(
        classLiteral.getName());

    return (replaceWith != null && replaceWith != classLiteral.getName())
        ? GWT.create(Class.forName(replaceWith)) : null;
  }

}
