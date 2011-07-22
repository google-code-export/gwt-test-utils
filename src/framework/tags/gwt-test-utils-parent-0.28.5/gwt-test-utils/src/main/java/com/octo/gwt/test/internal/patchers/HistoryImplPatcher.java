package com.octo.gwt.test.internal.patchers;

import java.util.Stack;

import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test.internal.AfterTestCallback;
import com.octo.gwt.test.internal.AfterTestCallbackManager;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(HistoryImpl.class)
class HistoryImplPatcher {

  static class HistoryHolder implements AfterTestCallback {

    Stack<String> stack = new Stack<String>();
    String top;

    HistoryHolder() {
      AfterTestCallbackManager.get().registerCallback(this);
    }

    public void afterTest() throws Throwable {
      stack.clear();
      top = null;
    }

  }

  static HistoryHolder HISTORY_HOLDER = new HistoryHolder();

  @PatchMethod
  static String getToken() {
    return HISTORY_HOLDER.top;
  }

  @PatchMethod
  static boolean init(HistoryImpl historyImpl) {
    return true;
  }

  @PatchMethod
  static void nativeUpdate(HistoryImpl historyImpl, String s) {

  }

  @PatchMethod
  static void setToken(String token) {
    HISTORY_HOLDER.stack.push(token);
    HISTORY_HOLDER.top = token;
  }

}
