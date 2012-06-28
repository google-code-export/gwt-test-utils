package com.googlecode.gwt.test.internal.junit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class GwtSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {

  public GwtSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  @Override
  public void run(RunNotifier notifier) {
    notifier.addListener(new GwtRunListener());
    super.run(notifier);
  }

}
