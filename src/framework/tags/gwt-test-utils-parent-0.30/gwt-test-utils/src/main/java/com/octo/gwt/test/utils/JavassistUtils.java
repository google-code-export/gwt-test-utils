package com.octo.gwt.test.utils;

import java.util.Set;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import com.google.gwt.dev.util.collect.HashSet;
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

    if (ctClass.getName().contains("CellBasedWi")) {
      System.out.println(ctClass);
    }

    Set<CtConstructor> set = new HashSet<CtConstructor>();

    findConstructors(ctClass, set);

    switch (set.size()) {
      case 0:
        return null;
      case 1:
        return set.iterator().next();
      default:
        throw new GwtTestPatchException("Multiple constructors (" + set.size()
            + ") in class " + ctClass.getName()
            + ", you have to set parameter types discriminators");
    }
  }

  private static void findConstructors(CtClass ctClass, Set<CtConstructor> set,
      Class<?>... argsClasses) {
    try {

      if (ctClass == null) {
        return;
      }

      CtConstructor[] constructors = ctClass.getDeclaredConstructors();

      if (constructors.length == 0) {
        findConstructors(ctClass.getSuperclass(), set, argsClasses);
      } else if (constructors.length == 1 && argsClasses.length == 0) {
        set.add(constructors[0]);
      } else {
        for (CtConstructor c : constructors) {

          if (c.getParameterTypes().length != argsClasses.length) {
            continue;
          }

          boolean sameArgs = true;
          for (int i = 0; i < argsClasses.length; i++) {
            String requestedClassName = argsClasses[i].getName();
            String currentClassName = c.getParameterTypes()[i].getName();

            if (!requestedClassName.equals(currentClassName)) {
              sameArgs = false;
            }
          }

          if (sameArgs) {
            set.add(c);
          }
        }
      }
    } catch (NotFoundException e) {
      // should never happen
      throw new GwtTestPatchException(
          "Error while trying find a constructor in class '"
              + ctClass.getName() + "'", e);
    }
  }

  private JavassistUtils() {

  }

}
