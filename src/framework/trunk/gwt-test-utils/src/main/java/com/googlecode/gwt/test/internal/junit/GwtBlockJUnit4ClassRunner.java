package com.googlecode.gwt.test.internal.junit;

import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.googlecode.gwt.test.internal.GwtConfig;

/**
 * gwt-test-utils {@link Runner}, which adds a {@link GwtRunListener} before
 * running each test. <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtBlockJUnit4ClassRunner extends BlockJUnit4ClassRunner {

  private final Class<?> klass;

  public GwtBlockJUnit4ClassRunner(Class<?> klass) throws InitializationError {
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
