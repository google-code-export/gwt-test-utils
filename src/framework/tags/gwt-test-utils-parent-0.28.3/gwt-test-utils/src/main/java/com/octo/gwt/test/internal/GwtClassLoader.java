package com.octo.gwt.test.internal;

import javassist.CannotCompileException;
import javassist.Loader;
import javassist.NotFoundException;

import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.Patcher;
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

  private static final String JSO_CLASS = "com.google.gwt.core.client.JavaScriptObject";

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

  /**
   * Bind a static instance of the classloader to the current thread and return
   * it.
   * 
   * @return the static classloader instance.
   * 
   * @see Thread#currentThread()
   * @see Thread#setContextClassLoader(ClassLoader)
   */
  public static GwtClassLoader get() {
    Thread.currentThread().setContextClassLoader(INSTANCE);

    return INSTANCE;
  }

  /**
   * Unbind the static classloader instance from the current thread by binding
   * the system classloader instead.
   */
  public static void reset() {
    Thread.currentThread().setContextClassLoader(INSTANCE.getParent());
  }

  private final GwtTranslator translator;

  private GwtClassLoader() throws NotFoundException, CannotCompileException {
    super(GwtClassPool.get());

    ConfigurationLoader configurationLoader = ConfigurationLoader.createInstance(
        this.getParent(), JSO_CLASS);

    for (String s : configurationLoader.getDelegateList()) {
      delegateLoadingOf(s);
    }

    translator = new GwtTranslator(configurationLoader.getPatchers(),
        JSO_CLASS, configurationLoader.getJSOSubClasses());

    addTranslator(GwtClassPool.get(), translator);
  }

}
