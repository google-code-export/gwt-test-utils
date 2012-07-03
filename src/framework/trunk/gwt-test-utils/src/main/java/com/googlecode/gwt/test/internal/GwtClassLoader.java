package com.googlecode.gwt.test.internal;

import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.shell.ModuleSpace;
import com.google.gwt.dev.shell.ModuleSpaceHost;
import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.exceptions.GwtTestException;
import com.googlecode.gwt.test.exceptions.GwtTestPatchException;

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

  private ProtectionDomain domain;

  private final TreeLogger logger = TreeLogger.NULL;

  private ModuleDef moduleDef;

  private ModuleSpace moduleSpace;

  private ModuleSpaceHost moduleSpaceHost;
  private final ClassPool source;
  private final Translator translator;

  private GwtClassLoader() throws NotFoundException, CannotCompileException {
    super(GwtClassPool.get());
    this.source = GwtClassPool.get();
    ConfigurationLoader configurationLoader = ConfigurationLoader.createInstance(this.getParent());
    this.translator = new GwtTranslator(configurationLoader);

    for (String s : configurationLoader.getDelegates()) {
      delegateLoadingOf(s);
    }

    addTranslator(GwtClassPool.get(), new GwtTranslator(configurationLoader));
  }

  @Override
  public void setDomain(ProtectionDomain domain) {
    super.setDomain(domain);
    this.domain = domain;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    byte[] classfile;
    try {
      translator.onLoad(source, name);

      try {
        classfile = source.get(name).toBytecode();
      } catch (NotFoundException e) {
        return null;
      }
    } catch (Exception e) {
      throw new ClassNotFoundException(
          "caught an exception while obtaining a class file for " + name, e);
    }

    int i = name.lastIndexOf('.');
    if (i != -1) {
      String pname = name.substring(0, i);
      if (getPackage(pname) == null)
        try {
          definePackage(pname, null, null, null, null, null, null, null);
        } catch (IllegalArgumentException e) {
          // ignore. maybe the package object for the same
          // name has been created just right away.
        }
    }

    if (domain == null)
      return defineClass(name, classfile, 0, classfile.length);
    else
      return defineClass(name, classfile, 0, classfile.length, domain);
  }

}
