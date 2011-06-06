package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(History.class)
public class HistoryPatcher extends AutomaticPatcher {

  @PatchMethod
  public static void back() {
    HistoryImplPatcher.stack.pop();
    String token = HistoryImplPatcher.stack.pop();
    HistoryImpl impl = GwtTestReflectionUtils.getStaticFieldValue(
        History.class, "impl");
    impl.fireHistoryChangedImpl(token);
  }

}
