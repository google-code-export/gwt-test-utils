package com.octo.gwt.test.internal.handlers;

import com.google.gwt.animation.client.AnimationScheduler;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class AnimationSchedulerCreateHandler implements GwtCreateHandler {

  public Object create(Class<?> classLiteral) throws Exception {
    if (!AnimationScheduler.class.equals(classLiteral)) {
      return null;
    }

    Class<?> implClass = Class.forName("com.google.gwt.animation.client.AnimationSchedulerImplTimer");
    return GwtReflectionUtils.instantiateClass(implClass);
  }

}
