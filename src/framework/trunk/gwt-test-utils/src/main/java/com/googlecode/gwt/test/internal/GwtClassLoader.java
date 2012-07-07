package com.googlecode.gwt.test.internal;

import java.io.IOException;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.exceptions.GwtTestException;
import com.googlecode.gwt.test.exceptions.GwtTestPatchException;
import com.googlecode.gwt.test.internal.rewrite.OverlayTypesRewriter;

/**
 * <p>
 * gwt-test-utils custom {@link ClassLoader} used to load classes referenced
 * inside a test class extending {@link GwtTest}.<strong>For internal use only :
 * do not refer directly to this classloader in your client code.</strong>
 * </p>
 * <p>
 * It aims to provide JVM-compliant versions of classes referenced in those test
 * classes. To obtain JVM-compliant code, this class loader relies on a set of
 * class {@link Patcher} which can be configured using the
 * <strong>META-INF\gwt-test-utils.properties</strong> file of your application.
 * </p>
 * 
 * <p>
 * In addition to {@link Patcher}, this classloader also perform some bytecode
 * modification on every GWT JavaScriptObject subtype, also known as
 * <strong>Overlay types</strong>.
 * </p>
 * 
 * 
 * @see GwtTranslator
 * @see OverlayTypesRewriter
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class GwtClassLoader extends Loader {

  private static class GwtClassLoaderWithRewriter extends GwtClassLoader {

    private final OverlayTypesRewriter overlayRewriter;

    private GwtClassLoaderWithRewriter(ConfigurationLoader configurationLoader,
        OverlayTypesRewriter overlayRewriter) throws NotFoundException,
        CannotCompileException {
      super(configurationLoader);

      this.overlayRewriter = overlayRewriter;
    }

    /**
     * Performs Overlay type support bytecode manipulation
     * 
     * @param className the name of the class to find
     * @param rewriter
     * @return the class byte array, or null if class has not been found
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    @Override
    protected byte[] findClassBytes(String className)
        throws ClassNotFoundException {

      if (overlayRewriter.isJsoIntf(className)) {
        // Generate a synthetic JSO interface class.
        return overlayRewriter.writeJsoIntf(className);
      }

      // A JSO impl class needs the class bytes for the original class.
      String classFromPool = (overlayRewriter.isJsoImpl(className))
          ? className.substring(0, className.length() - 1) : className;

      // Apply Patchers
      byte[] classBytes = super.findClassBytes(classFromPool);

      // perform Overlay types bytecode transformation
      classBytes = overlayRewriter.rewrite(className, classBytes);

      return classBytes;
    }
  }

  static GwtClassLoader createClassLoader(
      ConfigurationLoader configurationLoader,
      OverlayTypesRewriter overlayRewriter) {
    try {
      if (overlayRewriter == null) {
        return new GwtClassLoader(configurationLoader);
      } else {
        return new GwtClassLoaderWithRewriter(configurationLoader,
            overlayRewriter);
      }
    } catch (Exception e) {
      throw new GwtTestException("Error during "
          + GwtClassLoader.class.getSimpleName() + " instanciation :", e);
    }
  }

  private ProtectionDomain domain;
  private final ClassPool source;

  private final Translator translator;

  private GwtClassLoader(ConfigurationLoader configurationLoader)
      throws NotFoundException, CannotCompileException {
    super(GwtClassPool.get());
    this.source = GwtClassPool.get();
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
  protected Class<?> findClass(String className) throws ClassNotFoundException {
    byte[] classfile = findClassBytes(className);

    int i = className.lastIndexOf('.');
    if (i != -1) {
      String pname = className.substring(0, i);
      if (getPackage(pname) == null)
        try {
          definePackage(pname, null, null, null, null, null, null, null);
        } catch (IllegalArgumentException e) {
          // ignore. maybe the package object for the same
          // name has been created just right away.
        }
    }

    try {
      if (domain == null)
        return defineClass(className, classfile, 0, classfile.length);
      else
        return defineClass(className, classfile, 0, classfile.length, domain);
    } catch (Throwable t) {
      throw new GwtTestPatchException("Error while defining " + className
          + " from modified bytecode", t);
    }
  }

  protected byte[] findClassBytes(String className)
      throws ClassNotFoundException {
    try {
      // Apply Patchers
      translator.onLoad(source, className);
      try {
        return source.get(className).toBytecode();
      } catch (NotFoundException e) {
        return null;
      }
    } catch (Exception e) {
      throw new ClassNotFoundException(
          "caught an exception while obtaining a class file for " + className,
          e);
    }
  }

}
