package com.octo.gwt.test.utils;

import java.util.ArrayList;
import java.util.List;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import com.octo.gwt.test.exceptions.GwtTestPatchException;

/**
 * Utility class over the <code>javassist</code> API.
 * 
 * @author Gael Lazzari
 * 
 */
public class JavassistUtils {

  public static CtConstructor findConstructor(CtClass ctClass,
      Class<?>... argsClasses) {
    List<CtConstructor> l = new ArrayList<CtConstructor>();

    try {
      for (CtConstructor c : ctClass.getDeclaredConstructors()) {
        if (argsClasses == null
            || argsClasses.length == c.getParameterTypes().length) {
          l.add(c);

          if (argsClasses != null) {
            int i = 0;
            for (Class<?> argClass : argsClasses) {
              if (!argClass.getName().equals(c.getParameterTypes()[i].getName())) {
                l.remove(c);
                continue;
              }
              i++;
            }
          }
        }
      }
    } catch (NotFoundException e) {
      // should never happen
      throw new GwtTestPatchException(
          "Error while trying find a constructor in class '"
              + ctClass.getName() + "'", e);
    }

    if (l.size() == 1) {
      return l.get(0);
    }
    if (l.size() == 0) {
      throw new GwtTestPatchException(
          "Unable to find a constructor with the specifed parameter types in class "
              + ctClass.getName());
    }
    throw new GwtTestPatchException("Multiple constructor in class "
        + ctClass.getName()
        + ", you have to set parameter types discriminators");
  }

  private JavassistUtils() {

  }

}
