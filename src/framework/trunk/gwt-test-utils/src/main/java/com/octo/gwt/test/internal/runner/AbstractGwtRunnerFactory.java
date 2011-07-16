package com.octo.gwt.test.internal.runner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.Runner;

import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.internal.GwtClassLoader;

/**
 * Internal {@link Runner} factory. <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public abstract class AbstractGwtRunnerFactory {

  private static boolean hasJUnit45OrHigher;

  static {
    try {
      Class.forName("org.junit.runners.BlockJUnit4ClassRunner");
      hasJUnit45OrHigher = true;
    } catch (Throwable t) {
      hasJUnit45OrHigher = false;
    }
  }

  public Runner create(Class<?> clazz) throws InvocationTargetException {
    try {
      String runnerClassName = getRunnerClassName(hasJUnit45OrHigher);
      return newInstance(runnerClassName, clazz);
    } catch (InvocationTargetException e) {
      if (!hasTestMethods(clazz)) {
        throw new GwtTestConfigurationException("\n" + "\n"
            + "No tests found in " + clazz.getSimpleName() + "\n"
            + "Haven't you forgot @Test annotation?\n", e);
      }
      throw e;
    } catch (Throwable t) {
      throw new GwtTestConfigurationException(
          "gwt-test-utils can only be used with JUnit 4.4 or higher.\n"
              + "You can upgrade your JUnit version or write your own Runner (please consider contributing your runner to the gwt-test-utils community).",
          t);
    }
  }

  /**
   * Get the full qualified name of the JUnit {@link Runner} to use to run test
   * class according to the JUnit version available in the classpath.
   * 
   * @param hasJUnit45OrHigher True if JUnit 4.5 or higher is available, false
   *          otherwise.
   * @return The full qualified name of the JUnit {@link Runner} to use.
   */
  protected abstract String getRunnerClassName(boolean hasJUnit45OrHigher);

  private boolean hasTestMethods(Class<?> klass) {
    Method[] methods = klass.getMethods();
    for (Method m : methods) {
      if (m.isAnnotationPresent(Test.class)) {
        return true;
      }
    }
    return false;
  }

  private Runner newInstance(String runnerClassName, Class<?> constructorParam)
      throws Exception {
    Constructor<?> constructor;

    Class<?> runnerClass = GwtClassLoader.get().loadClass(runnerClassName);
    Class<?> testedClass = GwtClassLoader.get().loadClass(
        constructorParam.getName());
    constructor = runnerClass.getConstructor(Class.class.getClass());
    return (Runner) constructor.newInstance(testedClass);
  }

}
