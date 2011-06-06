package com.octo.gwt.test;

import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.Loader;
import javassist.NotFoundException;

import com.octo.gwt.test.internal.ConfigurationLoader;
import com.octo.gwt.test.internal.GwtTranslator;
import com.octo.gwt.test.internal.PatchGwtClassPool;

/**
 * GwtTestClassLoader is the class loader used to instantiate classes referenced
 * inside a test class extending {@link AbstractGwtTest}.
 */
public class GwtTestClassLoader extends Loader {

  private static GwtTestClassLoader INSTANCE;

  public static ClassLoader getInstance() {
    if (INSTANCE == null) {
      try {
        INSTANCE = new GwtTestClassLoader();
      } catch (Exception e) {
        if (e instanceof RuntimeException) {
          throw (RuntimeException) e;
        } else {
          throw new RuntimeException(e);
        }
      }
    }

    return INSTANCE;
  }

  private GwtTranslator translator;

  private GwtTestClassLoader() throws NotFoundException, CannotCompileException {
    super(PatchGwtClassPool.get());

    init();

    ConfigurationLoader configurationLoader = ConfigurationLoader.createInstance(this.getParent());

    for (String s : configurationLoader.getDelegateList()) {
      delegateLoadingOf(s);
    }
    for (String s : configurationLoader.getNotDelegateList()) {
      notDelegateLoadingOf(s);
    }

    translator = new GwtTranslator(configurationLoader.getPatchers());

    addTranslator(PatchGwtClassPool.get(), translator);
  }

  private List<String> delegate;

  private List<String> notDelegate;

  private void init() {
    if (delegate == null) {
      delegate = new ArrayList<String>();
    }
    if (notDelegate == null) {
      notDelegate = new ArrayList<String>();
    }
  }

  @Override
  public void delegateLoadingOf(String classname) {
    init();
    delegate.add(buildRegex(classname));
  }

  public void notDelegateLoadingOf(String classname) {
    init();
    notDelegate.add(buildRegex(classname));
  }

  private String buildRegex(String classname) {
    StringBuilder sb = new StringBuilder();
    sb.append("^");
    classname = classname.replaceAll("\\.", "\\\\\\.");
    classname = classname.replaceAll("\\*", ".*");

    if (classname.endsWith(".")) {
      classname = classname + ".*";
    }

    sb.append(classname);
    sb.append("$");
    return sb.toString();
  }

  protected Class<?> loadClassByDelegation(String classname)
      throws ClassNotFoundException {
    if (isDelegated(classname)) {
      return delegateToParent(classname);
    }
    return null;
  }

  private boolean isDelegated(String classname) {
    for (String pkg : notDelegate) {
      if (classname.matches(pkg)) {
        return false;
      }
    }
    for (String pkg : delegate) {
      if (classname.matches(pkg)) {
        return true;
      }
    }
    return false;
  }

}
