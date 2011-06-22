package com.octo.gwt.test.spring.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.TestClass;
import org.junit.runner.Runner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.octo.gwt.test.csv.internal.DirectoryTestReader;
import com.octo.gwt.test.internal.GwtClassLoader;
import com.octo.gwt.test.spring.GwtSpringCsvRunner;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * JUnit {@link Runner} implementation for spring CSV tests. Intent to be
 * wrapped by {@link GwtSpringCsvRunner}. <strong>For internal use
 * only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class Spring2CsvJUnit4ClassRunner extends SpringJUnit4ClassRunner {

  class CsvMethodValidator {
    private final List<Throwable> fErrors = new ArrayList<Throwable>();

    private final TestClass fTestClass;

    public CsvMethodValidator(TestClass testClass) {
      fTestClass = testClass;
    }

    public void assertValid() throws InitializationError {
      if (!fErrors.isEmpty())
        throw new InitializationError(fErrors);
    }

    public void validateInstanceMethods() {
      validateTestMethods(After.class, false);
      validateTestMethods(Before.class, false);
      validateTestMethods(Test.class, false);

      List<Method> methods = reader.getTestMethods();
      if (methods.size() == 0)
        fErrors.add(new Exception("No runnable methods"));
    }

    public List<Throwable> validateMethodsForDefaultRunner() {
      validateNoArgConstructor();
      validateStaticMethods();
      validateInstanceMethods();
      return fErrors;
    }

    public void validateNoArgConstructor() {
      try {
        fTestClass.getConstructor();
      } catch (Exception e) {
        fErrors.add(new Exception(
            "Test class should have public zero-argument constructor", e));
      }
    }

    public void validateStaticMethods() {
      validateTestMethods(BeforeClass.class, true);
      validateTestMethods(AfterClass.class, true);
    }

    private void validateTestMethods(Class<? extends Annotation> annotation,
        boolean isStatic) {
      List<Method> methods = fTestClass.getAnnotatedMethods(annotation);

      for (Method each : methods) {
        if (Modifier.isStatic(each.getModifiers()) != isStatic) {
          String state = isStatic ? "should" : "should not";
          fErrors.add(new Exception("Method " + each.getName() + "() " + state
              + " be static"));
        }
        if (!Modifier.isPublic(each.getDeclaringClass().getModifiers()))
          fErrors.add(new Exception("Class "
              + each.getDeclaringClass().getName() + " should be public"));
        if (!Modifier.isPublic(each.getModifiers()))
          fErrors.add(new Exception("Method " + each.getName()
              + " should be public"));
        if (each.getReturnType() != Void.TYPE)
          fErrors.add(new Exception("Method " + each.getName()
              + " should be void"));
        if (each.getParameterTypes().length != 0)
          fErrors.add(new Exception("Method " + each.getName()
              + " should have no parameters"));
      }
    }
  }

  private DirectoryTestReader reader;

  public Spring2CsvJUnit4ClassRunner(Class<?> clazz)
      throws InitializationError, ClassNotFoundException {
    super(GwtClassLoader.get().loadClass(clazz.getCanonicalName()));
  }

  @Override
  protected Object createTest() throws Exception {
    Object testInstance = reader.createObject();
    GwtReflectionUtils.callPrivateMethod(testInstance, "setReader", reader);
    getTestContextManager().prepareTestInstance(testInstance);
    return testInstance;
  }

  @Override
  protected List<Method> getTestMethods() {
    if (reader == null) {
      reader = new DirectoryTestReader(getTestClass().getJavaClass());
    }
    return reader.getTestMethods();
  }

  @Override
  protected void validate() throws InitializationError {
    CsvMethodValidator methodValidator = new CsvMethodValidator(getTestClass());
    methodValidator.validateMethodsForDefaultRunner();
    methodValidator.assertValid();
  }

}
