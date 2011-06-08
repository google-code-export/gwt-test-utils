package com.octo.gwt.test.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javassist.CtClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.Patcher;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.internal.ClassesScanner.ClassFilter;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class ConfigurationLoader {

  private static final String CONFIG_FILENAME = "META-INF/gwt-test-utils.properties";

  private static ConfigurationLoader INSTANCE;

  private static final Logger logger = LoggerFactory.getLogger(ConfigurationLoader.class);

  public static synchronized final ConfigurationLoader createInstance(
      ClassLoader classLoader) {
    if (INSTANCE != null) {
      throw new GwtTestConfigurationException(
          ConfigurationLoader.class.getSimpleName()
              + " instance has already been initialized");
    }

    INSTANCE = new ConfigurationLoader(classLoader);

    return INSTANCE;
  }

  public static synchronized ConfigurationLoader get() {
    if (INSTANCE == null) {
      throw new GwtTestConfigurationException(
          ConfigurationLoader.class.getSimpleName()
              + " instance has not been initialized yet");
    }

    return INSTANCE;
  }

  private final ClassLoader classLoader;

  private final ClassSubstituer classSubstituer;

  private final List<String> delegateList;
  private final MethodRemover methodRemover;
  private final Map<String, Patcher> patchers;
  private boolean processedModuleFile = false;
  private final Set<String> scanPackageSet;

  private ConfigurationLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
    this.classSubstituer = new ClassSubstituer();
    this.delegateList = new ArrayList<String>();
    this.scanPackageSet = new HashSet<String>();
    this.methodRemover = new MethodRemover();
    this.patchers = new HashMap<String, Patcher>();

    readFiles();
    loadPatchersAndJavaScriptObjects();
  }

  public JavaClassModifier getClassSubstituer() {
    return classSubstituer;
  }

  public List<String> getDelegateList() {
    return Collections.unmodifiableList(delegateList);
  }

  public JavaClassModifier getMethodRemover() {
    return methodRemover;
  }

  public Map<String, Patcher> getPatchers() {
    return patchers;
  }

  private boolean hasDefaultConstructor(Class<?> clazz) {
    try {
      return clazz.getDeclaredConstructor() != null;
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  private void loadPatchersAndJavaScriptObjects() {
    ClassFilter patchClassFilter = new ClassFilter() {

      public boolean accept(CtClass ctClass) {
        return ctClass.hasAnnotation(PatchClass.class);
      }
    };

    Set<CtClass> patchClasses = ClassesScanner.getInstance().findClasses(
        patchClassFilter, scanPackageSet);

    for (CtClass patchClass : patchClasses) {
      treatPatchClass(patchClass);
    }
  }

  private void process(Properties p, URL url) {
    for (Entry<Object, Object> entry : p.entrySet()) {
      String key = (String) entry.getKey();
      String value = (String) entry.getValue();
      if ("scan-package".equals(value)) {
        if (!scanPackageSet.add(key)) {
          throw new GwtTestConfigurationException(
              "'scan-package' mechanism is used to scan the same package twice : '"
                  + key + "'");
        }
      } else if ("delegate".equals(value)) {
        delegateList.add(key);
      } else if ("remove-method".equals(value)) {
        processRemoveMethod(key, url);
      } else if ("substitute-class".equals(value)) {
        processSubstituteClass(key, url);
      } else if ("module-file".equals(value)) {
        processModuleFile(key, url);
        processedModuleFile = true;
      } else {
        throw new GwtTestConfigurationException("Error in '" + url.getPath()
            + "' : unknown value '" + value + "'");
      }
    }
  }

  private void processModuleFile(String key, URL url) {
    ModuleData.get().parseModule(key);
  }

  private void processRemoveMethod(String key, URL url) {
    String[] array = key.split("\\s*,\\s*");
    if (array.length != 3) {
      throw new GwtTestConfigurationException(
          "Invalid 'remove-method' property format for value '" + key
              + "' in file '" + url.getPath() + "'");
    }

    String methodClass = array[0];
    String methodName = array[1];
    String methodSignature = array[2];

    methodRemover.removeMethod(methodClass, methodName, methodSignature);
  }

  private void processSubstituteClass(String key, URL url) {
    String[] array = key.split("\\s*,\\s*");
    if (array.length != 2) {
      throw new GwtTestConfigurationException(
          "Invalid 'substitute-class' property format for value '" + key
              + "' in file '" + url.getPath() + "'");
    }

    String originalClass = array[0];
    String substitutionClass = array[1];
    // add in second position, just after method-substituer
    this.classSubstituer.registerSubstitution(originalClass, substitutionClass);
  }

  private void readFiles() {
    try {
      Enumeration<URL> l = classLoader.getResources(CONFIG_FILENAME);
      while (l.hasMoreElements()) {
        URL url = l.nextElement();
        logger.debug("Load config file " + url.toString());
        Properties p = new Properties();
        InputStream inputStream = url.openStream();
        p.load(inputStream);
        inputStream.close();
        process(p, url);
        logger.debug("File loaded and processed " + url.toString());
      }
    } catch (IOException e) {
      throw new GwtTestConfigurationException("Error while reading '"
          + CONFIG_FILENAME + "' files", e);
    }

    // check that at least one module file has been processed
    if (!processedModuleFile) {
      throw new GwtTestConfigurationException(
          "Cannot find any 'module-file' setup in configuration file 'META-INF/gwt-test-utils.properties'");
    }
  }

  private void treatPatchClass(CtClass patchCtClass) {
    Class<?> clazz = null;
    try {
      clazz = Class.forName(patchCtClass.getName(), true, classLoader);
    } catch (ClassNotFoundException e) {
      throw new GwtTestConfigurationException(
          "Cannot find class in the classpath : '" + patchCtClass.getName()
              + "'");
    } catch (NoClassDefFoundError e) {
      throw new GwtTestConfigurationException(
          "No class definition found in the classpath for : '" + e.getMessage()
              + "'");
    }
    PatchClass patchClass = GwtReflectionUtils.getAnnotation(clazz,
        PatchClass.class);

    if (!Patcher.class.isAssignableFrom(clazz) || !hasDefaultConstructor(clazz)) {
      throw new GwtTestConfigurationException("The @"
          + PatchClass.class.getSimpleName() + " annotated class '"
          + clazz.getName() + "' must implements "
          + Patcher.class.getSimpleName()
          + " interface and provide an empty constructor");
    }

    Patcher patcher = (Patcher) GwtReflectionUtils.instantiateClass(clazz);

    for (Class<?> c : patchClass.value()) {
      String targetName = c.isMemberClass()
          ? c.getDeclaringClass().getCanonicalName() + "$" + c.getSimpleName()
          : c.getCanonicalName();
      if (patchers.get(targetName) != null) {
        logger.error("Two patches for same class " + targetName);
        throw new GwtTestConfigurationException("Two patches for same class "
            + targetName);
      }
      patchers.put(targetName, patcher);
      logger.debug("Add patch for class " + targetName + " : "
          + clazz.getCanonicalName());
    }
    for (String s : patchClass.classes()) {
      if (patchers.get(s) != null) {
        logger.error("Two patches for same class " + s);
        throw new GwtTestConfigurationException("Two patches for same class "
            + s);
      }
      patchers.put(s, patcher);
      logger.debug("Add patch for class " + s + " : "
          + clazz.getCanonicalName());
    }
  }

}
