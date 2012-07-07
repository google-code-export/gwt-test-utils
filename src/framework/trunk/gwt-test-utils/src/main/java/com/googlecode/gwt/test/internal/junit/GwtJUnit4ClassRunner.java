package com.googlecode.gwt.test.internal.junit;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import com.googlecode.gwt.test.internal.GwtConfig;

/**
 * 
 * gwt-test-utils {@link Runner}, which adds a {@link GwtRunListener} before
 * running each test. <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 * @deprecated Included for backwards compatibility with JUnit 4.4.
 * 
 */
@Deprecated
public class GwtJUnit4ClassRunner extends JUnit4ClassRunner {

  private final Class<?> klass;

  public GwtJUnit4ClassRunner(Class<?> klass) throws InitializationError {
    super(klass);
    this.klass = klass;
  }

  @Override
  public void run(RunNotifier notifier) {
    GwtConfig.get().setTestClass(klass);
    notifier.addListener(new GwtRunListener());
    super.run(notifier);
  }

}
