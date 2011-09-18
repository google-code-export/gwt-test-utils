package com.octo.gwt.test.internal;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.user.client.ui.LayoutCommand;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(LayoutCommand.class)
class LayoutCommandPatcher {

  /*
   * To replace Scheduler.get().scheduleFinally(this) by
   * Scheduler.get().scheduleDeferred(this)
   * 
   * @param cmd
   * 
   * @param duration
   * 
   * @param callback
   */
  @PatchMethod
  static void schedule(LayoutCommand cmd, int duration,
      AnimationCallback callback) {

    GwtReflectionUtils.setPrivateFieldValue(cmd, "duration", duration);
    GwtReflectionUtils.setPrivateFieldValue(cmd, "callback", callback);

    GwtReflectionUtils.setPrivateFieldValue(cmd, "canceled", false);

    boolean scheduled = (Boolean) GwtReflectionUtils.getPrivateFieldValue(cmd,
        "scheduled");

    if (!scheduled) {
      GwtReflectionUtils.setPrivateFieldValue(cmd, "scheduled", true);
      Scheduler.get().scheduleDeferred(cmd);
    }
  }

}
