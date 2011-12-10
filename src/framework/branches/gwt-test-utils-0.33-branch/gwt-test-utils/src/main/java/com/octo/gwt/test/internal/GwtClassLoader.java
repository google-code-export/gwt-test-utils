package com.octo.gwt.test.internal;

import javassist.CannotCompileException;
import javassist.Loader;
import javassist.NotFoundException;

import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestPatchException;

/**
 * <p>
 * gwt-test-utils custom {@link ClassLoader} used to load classes referenced
 * inside a test class extending {@link GwtTest}.<strong>For internal use only :
 * do not refer directly to this classloader in your client code.</strong>
 * </p>
 * <p>
 * Its aim is to provide JVM-compliant versions of classes referenced in those
 * test classes. To obtain JVM-compliant code, the class loader relies on a set
 * of class {@link Patcher} which can be configured using the
 * <strong>META-INF\gwt-test-utils.properties</strong> file of your application.
 * </p>
 * 
 * @see GwtTranslator
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class GwtClassLoader extends Loader {

  private static final GwtClassLoader INSTANCE;

  static {
    try {
      INSTANCE = new GwtClassLoader();
    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestPatchException(e);
      }
    }
  }

  public static GwtClassLoader get() {
    return INSTANCE;
  }

  private GwtClassLoader() throws NotFoundException, CannotCompileException {
    super(GwtClassPool.get());

    ConfigurationLoader configurationLoader = ConfigurationLoader.createInstance(this.getParent());

    for (String s : configurationLoader.getDelegateSet()) {
      delegateLoadingOf(s);
    }

    addTranslator(GwtClassPool.get(), new GwtTranslator());
  }

}
