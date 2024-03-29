package com.googlecode.gwt.test.internal.patchers;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.googlecode.gwt.test.internal.AsyncCallbackRecorder;
import com.googlecode.gwt.test.patchers.InitMethod;

// @PatchClass(SimpleEventBus.class)
class SimpleEventBusPatcher {

  @InitMethod
  static void initCtClass(CtClass c) throws NotFoundException,
      CannotCompileException {
    CtMethod doFire = c.getDeclaredMethod("doFire");

    doFire.insertBefore(AsyncCallbackRecorder.class.getName()
        + ".get().recordAsyncCalls();");
    doFire.insertAfter(AsyncCallbackRecorder.class.getName()
        + ".get().triggerRecordedAsyncCallbacks();");
  }

}
