package com.octo.gwt.test.spring.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.octo.gwt.test.csv.internal.DirectoryTestReader;
import com.octo.gwt.test.internal.GwtClassLoader;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class Spring3CsvJUnit4ClassRunner extends SpringJUnit4ClassRunner {

  private DirectoryTestReader reader;

  public Spring3CsvJUnit4ClassRunner(Class<?> clazz) throws InitializationError,
      ClassNotFoundException {
    super(GwtClassLoader.get().loadClass(clazz.getCanonicalName()));
  }

  @Override
  protected List<FrameworkMethod> computeTestMethods() {
    if (reader == null) {
      reader = new DirectoryTestReader(getTestClass().getJavaClass());
    }
    List<FrameworkMethod> frameworkMethods = new ArrayList<FrameworkMethod>();
    for (Method csvMethod : reader.getTestMethods()) {
      frameworkMethods.add(new FrameworkMethod(csvMethod));
    }

    return frameworkMethods;
  }

  @Override
  protected Object createTest() throws Exception {
    Object testInstance = reader.createObject();
    GwtReflectionUtils.callPrivateMethod(testInstance, "setReader", reader);
    getTestContextManager().prepareTestInstance(testInstance);
    return testInstance;
  }

}
