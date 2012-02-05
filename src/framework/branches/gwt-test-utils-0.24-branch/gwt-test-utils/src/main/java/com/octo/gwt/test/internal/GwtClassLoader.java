package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.Loader;
import javassist.NotFoundException;

import com.octo.gwt.test.GwtTest;

/**
 * GwtTestClassLoader is the class loader used to instantiate classes referenced
 * inside a test class extending {@link GwtTest}.
 */
public class GwtClassLoader extends Loader {

  private static GwtClassLoader INSTANCE;

  public static ClassLoader getInstance() {
    if (INSTANCE == null) {
      try {
        INSTANCE = new GwtClassLoader();
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

  private List<String> delegate;

  private List<String> notDelegate;

  private final GwtTranslator translator;

  private GwtClassLoader() throws NotFoundException, CannotCompileException {
    super(GwtClassPool.get());

    init();

    ConfigurationLoader configurationLoader = ConfigurationLoader.createInstance(this.getParent());

    for (String s : configurationLoader.getDelegates()) {
      delegateLoadingOf(s);
    }
    for (String s : configurationLoader.getNotDelegates()) {
      notDelegateLoadingOf(s);
    }

    translator = new GwtTranslator(configurationLoader);

    addTranslator(GwtClassPool.get(), translator);
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

  @Override
  protected Class<?> loadClassByDelegation(String classname)
      throws ClassNotFoundException {
    if (isDelegated(classname)) {
      return delegateToParent(classname);
    }
    return null;
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

  private void init() {
    if (delegate == null) {
      delegate = new ArrayList<String>();
    }
    if (notDelegate == null) {
      notDelegate = new ArrayList<String>();
    }
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
