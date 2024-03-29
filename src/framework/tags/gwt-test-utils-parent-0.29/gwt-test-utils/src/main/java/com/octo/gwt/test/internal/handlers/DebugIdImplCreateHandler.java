package com.octo.gwt.test.internal.handlers;

import com.google.gwt.user.client.ui.UIObject.DebugIdImpl;
import com.google.gwt.user.client.ui.UIObject.DebugIdImplEnabled;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.internal.GwtConfig;

class DebugIdImplCreateHandler implements GwtCreateHandler {

  private static final DebugIdImpl disabledInstance = new DebugIdImpl();

  private static final DebugIdImpl enabledInstance = new DebugIdImplEnabled();

  public Object create(Class<?> classLiteral) throws Exception {
    if (DebugIdImpl.class != classLiteral) {
      return null;
    }

    return GwtConfig.get().ensureDebugId() ? enabledInstance : disabledInstance;

  }

}
