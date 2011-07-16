package com.octo.gwt.test.internal.runner;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;

/**
 * <p>
 * Base class for gwt-test-utils JUnit {@link Runner}, which aims to wrap an
 * existing {@link Runner} implementation
 * </p>
 * 
 * @author Gael Lazzari
 * 
 */
public abstract class AbstractGwtRunner extends Runner implements Filterable {

  private final Runner runner;

  public AbstractGwtRunner(Class<?> clazz) throws Exception {
    runner = getRunnerFactory().create(clazz);
  }

  public void filter(Filter filter) throws NoTestsRemainException {
    if (Filterable.class.isInstance(runner)) {
      ((Filterable) runner).filter(filter);
    }
  }

  @Override
  public Description getDescription() {
    return runner.getDescription();
  }

  @Override
  public void run(RunNotifier notifier) {
    runner.run(notifier);
  }

  protected abstract AbstractGwtRunnerFactory getRunnerFactory();

}
