package com.octo.gwt.test.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javassist.CtClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.ClassesScanner.ClassVisitor;
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

  private final Set<String> delegateSet;
  private final MethodRemover methodRemover;
  private final Set<String> moduleFileSet;
  private PatcherFactory patcherFactory;
  private boolean processedModuleFile = false;
  private final Set<String> removeMethodSet;
  private final Set<String> scanPackageSet;
  private final Set<String> substituteClassSet;

  private ConfigurationLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
    this.classSubstituer = new ClassSubstituer();
    this.delegateSet = new HashSet<String>();
    this.moduleFileSet = new HashSet<String>();
    this.scanPackageSet = new HashSet<String>();
    this.removeMethodSet = new HashSet<String>();
    this.substituteClassSet = new HashSet<String>();

    this.methodRemover = new MethodRemover();

    readFiles();
    visitPatchClasses();
  }

  public JavaClassModifier getClassSubstituer() {
    return classSubstituer;
  }

  public Set<String> getDelegateSet() {
    return Collections.unmodifiableSet(delegateSet);
  }

  public JavaClassModifier getMethodRemover() {
    return methodRemover;
  }

  public PatcherFactory getPatcherFactory() {
    return patcherFactory;
  }

  private void process(Properties p, URL url) {
    for (Entry<Object, Object> entry : p.entrySet()) {
      String key = ((String) entry.getKey()).trim();
      String value = ((String) entry.getValue()).trim();
      if ("scan-package".equals(value)) {
        scanPackageSet.add(key);
      } else if ("delegate".equals(value)) {
        delegateSet.add(key);
      } else if ("remove-method".equals(value)
          && !removeMethodSet.contains(key)) {
        processRemoveMethod(key, url);
        removeMethodSet.add(key);
      } else if ("substitute-class".equals(value)
          && !substituteClassSet.contains(key)) {
        processSubstituteClass(key, url);
        substituteClassSet.add(key);
      } else if ("module-file".equals(value) && !moduleFileSet.contains(key)) {
        processModuleFile(key, url);
        processedModuleFile = true;
        moduleFileSet.add(key);
      } else {
        throw new GwtTestConfigurationException("Error in '" + url.getPath()
            + "' : unknown value '" + value + "'");
      }
    }
  }

  private void processModuleFile(String string, URL url) {
    ModuleData.get().parseModule(string);
  }

  private void processRemoveMethod(String string, URL url) {
    String[] array = string.split("\\s*,\\s*");
    if (array.length != 3) {
      throw new GwtTestConfigurationException(
          "Invalid 'remove-method' property format for value '" + string
              + "' in file '" + url.getPath() + "'");
    }

    String methodClass = array[0];
    String methodName = array[1];
    String methodSignature = array[2];

    methodRemover.removeMethod(methodClass, methodName, methodSignature);
  }

  private void processSubstituteClass(String string, URL url) {
    String[] array = string.split("\\s*,\\s*");
    if (array.length != 2) {
      throw new GwtTestConfigurationException(
          "Invalid 'substitute-class' property format for value '" + string
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

  private void visitPatchClasses() {
    final Map<String, Set<CtClass>> patchClassMap = new HashMap<String, Set<CtClass>>();
    ClassVisitor patchClassVisitor = new ClassVisitor() {

      public void visit(CtClass ctClass) {
        PatchClass annotation = null;
        try {
          if (ctClass.hasAnnotation(PatchClass.class)) {
            // load the Patch class in the main classloader
            Class<?> patchClass = Class.forName(ctClass.getName(), false,
                classLoader);
            annotation = GwtReflectionUtils.getAnnotation(patchClass,
                PatchClass.class);
          }
        } catch (ClassNotFoundException e) {
          // should never happen
          throw new GwtTestPatchException(e);
        }

        if (annotation != null) {
          String classToPatchName = annotation.value() != PatchClass.class
              ? annotation.value().getName() : annotation.target().trim();

          if (!"".equals(classToPatchName)) {
            addPatchClass(classToPatchName, ctClass);
          }
        }
      }

      private void addPatchClass(String targetName, CtClass patchClass) {
        Set<CtClass> patchClasses = patchClassMap.get(targetName);
        if (patchClasses == null) {
          patchClasses = new HashSet<CtClass>();
          patchClassMap.put(targetName, patchClasses);
        }

        patchClasses.add(patchClass);

        logger.debug("Add patch for class '" + targetName + "' : '"
            + patchClass.getName() + "'");
      }

    };

    ClassesScanner.getInstance().scanPackages(patchClassVisitor, scanPackageSet);

    // create all patchers
    patcherFactory = new PatcherFactory(patchClassMap);
  }

}
