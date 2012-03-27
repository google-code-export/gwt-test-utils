package com.googlecode.gwt.test.csv.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.googlecode.gwt.test.utils.GwtReflectionUtils;

/**
 * 
 * @author Gael Lazzari
 * 
 */
public class BlockJUnit4CsvRunner extends BlockJUnit4ClassRunner {

  private DirectoryTestReader reader;

  public BlockJUnit4CsvRunner(Class<?> clazz) throws InitializationError,
      ClassNotFoundException {
    super(clazz);
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
    return testInstance;
  }

}
