package com.googlecode.gwt.test.internal.handlers;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

import com.googlecode.gwt.test.GwtCreateHandler;
import com.googlecode.gwt.test.internal.GwtClassLoader;
import com.googlecode.gwt.test.internal.GwtClassPool;
import com.googlecode.gwt.test.internal.GwtPatcherUtils;
import com.googlecode.gwt.test.utils.JavassistUtils;

/**
 * <p>
 * Handler for trying to automatically instanciate abstract classes, by
 * subclassing them and replace each abstract method by a default implementation
 * which would throws an {@link UnsupportedOperationException}.
 * </p>
 * <p>
 * It has been introducted to make possible the instanciation of abstract
 * classes that gwt-test-utils doesn't patch right now.
 * </p>
 * 
 * @see GwtPatcherUtils#patch(CtClass, com.googlecode.gwt.test.internal.Patcher)
 * 
 * @author Gael Lazzari
 * 
 */
class AbstractClassCreateHandler implements GwtCreateHandler {

  private final Map<Class<?>, Class<?>> cache = new HashMap<Class<?>, Class<?>>();

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtCreateHandler#create(java.lang.Class)
   */
  public Object create(Class<?> classLiteral) throws Exception {
    if (classLiteral.isAnnotation() || classLiteral.isArray()
        || classLiteral.isEnum() || classLiteral.isInterface()
        || !Modifier.isAbstract(classLiteral.getModifiers())) {
      return null;
    }

    Class<?> newClass = cache.get(classLiteral);

    if (newClass != null) {
      return newClass.newInstance();
    }

    CtClass ctClass = GwtClassPool.getCtClass(classLiteral);
    CtClass subClass = GwtClassPool.get().makeClass(
        classLiteral.getCanonicalName() + "SubClass");

    subClass.setSuperclass(ctClass);

    subClass.setModifiers(Modifier.PUBLIC);

    // handle case where the default constructor is declared in a super class
    CtConstructor defaultConstructor = JavassistUtils.findConstructor(subClass);
    if (defaultConstructor.getDeclaringClass() != subClass) {
      defaultConstructor = new CtConstructor(defaultConstructor, subClass, null);
      subClass.addConstructor(defaultConstructor);
    }

    if (!Modifier.isPublic(defaultConstructor.getModifiers())) {
      defaultConstructor.setModifiers(Modifier.PUBLIC);
    }

    for (CtMethod m : ctClass.getDeclaredMethods()) {
      if (javassist.Modifier.isAbstract(m.getModifiers())) {
        CtMethod copy = new CtMethod(m, subClass, null);
        subClass.addMethod(copy);
      }
    }

    GwtPatcherUtils.patch(subClass, null);

    newClass = subClass.toClass(GwtClassLoader.get(), null);
    cache.put(classLiteral, newClass);

    return newClass.newInstance();
  }

}
