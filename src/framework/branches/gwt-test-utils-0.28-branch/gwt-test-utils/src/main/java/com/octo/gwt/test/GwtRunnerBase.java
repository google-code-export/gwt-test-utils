package com.octo.gwt.test;

import java.lang.reflect.Constructor;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;

import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.internal.GwtClassLoader;

/**
 * <p>
 * The base gwt-test-utils test {@link Runner}. It provide a mecanism to wrap
 * another JUnit Runner which will be loaded by the {@link GwtClassLoader}. This
 * way, all classes referenced by the current test class will be loaded by the
 * custom classloader.
 * </p>
 * 
 * @author Gael Lazzari
 * 
 */
public abstract class GwtRunnerBase extends Runner implements Filterable,
    Sortable {

  private final Runner runner;

  public GwtRunnerBase(Class<?> clazz) throws Exception {
    Class<?> runnerClass = GwtClassLoader.get().loadClass(
        getClassRunnerClassName());

    if (!Runner.class.isAssignableFrom(runnerClass)) {
      throw new GwtTestConfigurationException("Cannot create a valid JUnit "
          + Runner.class.getSimpleName() + " : class '"
          + getClassRunnerClassName() + "' does not extend '"
          + Runner.class.getName() + "'");
    }

    Constructor<?> cons = runnerClass.getConstructor(Class.class);

    runner = (Runner) cons.newInstance(GwtClassLoader.get().loadClass(
        clazz.getCanonicalName()));
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

  public void sort(Sorter sorter) {
    if (Sortable.class.isInstance(runner)) {
      ((Sortable) runner).sort(sorter);
    }
  }

  @Override
  public int testCount() {
    return runner.testCount();
  }

  protected abstract String getClassRunnerClassName();

}
