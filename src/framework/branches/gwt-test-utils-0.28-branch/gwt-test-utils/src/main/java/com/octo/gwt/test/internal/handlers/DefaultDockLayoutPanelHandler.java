package com.octo.gwt.test.internal.handlers;

import java.lang.reflect.Constructor;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class DefaultDockLayoutPanelHandler implements GwtCreateHandler {

  public Object create(Class<?> classLiteral) throws Exception {
    if (!DockLayoutPanel.class.isAssignableFrom(classLiteral)) {
      return null;
    }

    Constructor<?> cons = classLiteral.getConstructor(Unit.class);
    return GwtReflectionUtils.instantiateClass(cons, Unit.EM);
  }

}
