package com.octo.gwt.test.internal.handlers;

import java.util.HashSet;
import java.util.Set;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.internal.ModuleData;

public class DeferredGenerateWithCreateHandler implements GwtCreateHandler {

  private Set<Class<?>> customGeneratedClasses;

  public Object create(Class<?> classLiteral) throws Exception {
    if (customGeneratedClasses == null) {
      customGeneratedClasses = initCustomGeneratedClasses();
    }

    for (Class<?> clazz : customGeneratedClasses) {
      if (clazz.isAssignableFrom(classLiteral)) {
        throw new GwtTestConfigurationException(
            "A custom Generator should be used to instanciate '"
                + classLiteral.getName()
                + "', but gwt-test-utils does not support GWT compiler API, so you have to add our own GwtCreateHandler with 'GwtTest.addGwtCreateHandler(..)' method or to declare your tested object with @Mock");
      }
    }

    return null;
  }

  private Set<Class<?>> initCustomGeneratedClasses() {
    Set<Class<?>> result = new HashSet<Class<?>>();
    for (String className : ModuleData.get().getCustomGeneratedClasses()) {
      try {
        result.add(Class.forName(className));
      } catch (ClassNotFoundException e) {
        throw new GwtTestConfigurationException(
            "Cannot find class configured to be instanced with a custom 'generate-with' Generator : '"
                + className + "'");
      }
    }

    return result;
  }

}
