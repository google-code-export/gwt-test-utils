package com.octo.gwt.test.internal.patchers;

import java.util.Stack;

import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(HistoryImpl.class)
public class HistoryImplPatcher {

  private static String top = null;

  static Stack<String> stack = new Stack<String>();

  public static void reset() {
    stack.clear();
  }

  @PatchMethod
  static String getToken() {
    return top;
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
    stack.push(token);
    top = token;
  }

}
