package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(History.class)
public class HistoryPatcher extends AutomaticPatcher {

  @PatchMethod
  public static void back() {
    HistoryImplPatcher.stack.pop();
    String token = HistoryImplPatcher.stack.pop();
    HistoryImpl impl = GwtReflectionUtils.getStaticFieldValue(History.class,
        "impl");
    impl.fireHistoryChangedImpl(token);
  }

}
