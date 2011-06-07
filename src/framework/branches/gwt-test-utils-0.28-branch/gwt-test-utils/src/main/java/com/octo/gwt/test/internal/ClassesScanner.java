package com.octo.gwt.test.internal;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javassist.CtClass;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.exceptions.GwtTestPatchException;

/**
 * The class used to scan and filter classes.
 * 
 * @author Gael Lazzari
 * 
 */
class ClassesScanner {

  static interface ClassFilter {

    boolean accept(CtClass ctClass);
  }

  private static final ClassesScanner INSTANCE = new ClassesScanner();

  private static Logger logger = LoggerFactory.getLogger(ClassesScanner.class);

  public static ClassesScanner getInstance() {
    return INSTANCE;
  }

  private ClassesScanner() {
  }

  public Set<CtClass> findClasses(ClassFilter classFilter,
      Set<String> rootPackages) {
    Set<CtClass> resultSet = new HashSet<CtClass>();

    for (String rootPackage : rootPackages) {
      String path = rootPackage.replaceAll("\\.", "/");
      logger.debug("Scan package " + rootPackage);
      try {
        Enumeration<URL> l = Thread.currentThread().getContextClassLoader().getResources(
            path);
        while (l.hasMoreElements()) {
          URL url = l.nextElement();
          String u = url.toExternalForm();
          if (u.startsWith("file:")) {
            String directoryName = u.substring("file:".length());
            directoryName = URLDecoder.decode(directoryName, "UTF-8");
            loadClassesFromDirectory(new File(directoryName), rootPackage,
                classFilter, resultSet);
          } else if (u.startsWith("jar:file:")) {
            loadClassesFromJarFile(u.substring("jar:file:".length()),
                rootPackage, classFilter, resultSet);
          } else {
            throw new IllegalArgumentException("Not managed class container "
                + u);
          }
        }
      } catch (Exception e) {
        throw new GwtTestPatchException("Error while scanning package '"
            + rootPackage + "'", e);
      }
    }
    return resultSet;
  }

  private Set<CtClass> getCtClasses(String classFileName,
      ClassFilter classFilter) {
    Set<CtClass> set = new HashSet<CtClass>();

    try {
      CtClass current = GwtClassPool.getClass(classFileName.substring(0,
          classFileName.length() - ".class".length()));

      if (classFilter.accept(current)) {
        set.add(current);
      }

      for (CtClass innerClass : current.getNestedClasses()) {
        if (classFilter.accept(innerClass)) {
          set.add(innerClass);
        }
      }

    } catch (NotFoundException e) {
      // do nothing
    }

    return set;
  }

  private void loadClassesFromDirectory(File directoryToScan,
      String scanPackage, ClassFilter classFilter, Set<CtClass> set) {
    logger.debug("Scan directory " + directoryToScan);
    for (File f : directoryToScan.listFiles()) {
      if (f.isDirectory()) {
        if (!".".equals(f.getName()) && !"..".equals(f.getName())) {
          loadClassesFromDirectory(f, scanPackage + "." + f.getName(),
              classFilter, set);
        }
      } else {
        if (f.getName().endsWith(".class")) {
          set.addAll(getCtClasses(scanPackage + "." + f.getName(), classFilter));
        }
      }
    }
    logger.debug("Directory scanned " + directoryToScan);
  }

  private void loadClassesFromJarFile(String path, String scanPackage,
      ClassFilter classFilter, Set<CtClass> set) throws Exception {
    String prefix = path.substring(path.indexOf("!") + 2);
    String jarName = path.substring(0, path.indexOf("!"));
    jarName = URLDecoder.decode(jarName, "UTF-8");
    logger.debug("Load classes from jar " + jarName);
    JarFile jar = new JarFile(jarName);
    Enumeration<JarEntry> entries = jar.entries();
    while (entries.hasMoreElements()) {
      JarEntry entry = entries.nextElement();
      if (entry.getName().startsWith(prefix)
          && entry.getName().endsWith(".class")) {
        set.addAll(getCtClasses(entry.getName().replaceAll("\\/", "."),
            classFilter));
      }
    }
    logger.debug("Classes loaded from jar " + jarName);
  }

}
