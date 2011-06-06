package com.octo.gwt.test.integ.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.integ.CsvDirectory;
import com.octo.gwt.test.integ.CsvMacros;
import com.octo.gwt.test.integ.csvrunner.CsvReader;
import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class DirectoryTestReader {

  private Class<?> generatedClazz;

  private Map<String, List<List<String>>> tests;

  private Map<String, List<List<String>>> macros;

  private List<Method> testMethods;

  public DirectoryTestReader(Class<?> clazz) {
    try {
      CsvDirectory csvDirectoryAnnotation = getAnnotation(clazz,
          CsvDirectory.class);
      CsvMacros csvMacrosAnnotation = getAnnotation(clazz, CsvMacros.class);

      initCsvTests(csvDirectoryAnnotation);
      initCsvMacros(csvMacrosAnnotation);
      initTestMethods(clazz, csvDirectoryAnnotation);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void initCsvTests(CsvDirectory csvDirectory)
      throws FileNotFoundException, IOException {
    File directory = getDirectory(csvDirectory.value());

    testMethods = new ArrayList<Method>();

    tests = new HashMap<String, List<List<String>>>();
    for (File f : directory.listFiles()) {
      if (f.getName().endsWith(csvDirectory.extension())) {
        String s = f.getName();
        s = s.substring(0, s.length() - 4);
        tests.put(s, CsvReader.readCsv(new FileReader(f)));
      }
    }
  }

  private void initCsvMacros(CsvMacros csvMacros) throws FileNotFoundException,
      IOException {
    Pattern macroNamePattern = (csvMacros.pattern() != null)
        ? Pattern.compile(csvMacros.pattern()) : null;
    File macrosDirectory = getDirectory(csvMacros.value());

    macros = new HashMap<String, List<List<String>>>();
    for (File file : macrosDirectory.listFiles()) {
      String fileName = file.getName();
      if (macroNamePattern == null
          || macroNamePattern.matcher(file.getName()).matches()) {
        FileReader reader = new FileReader(file);
        List<List<String>> sheet = CsvReader.readCsv(reader);
        macros.put(fileName, sheet);
      }
    }
  }

  private void initTestMethods(Class<?> clazz, CsvDirectory csvDirectory)
      throws Exception {
    testMethods = new ArrayList<Method>();
    String csvShortName = csvDirectory.value().substring(
        csvDirectory.value().lastIndexOf('/') + 1);
    ClassPool cp = PatchGwtClassPool.get();

    CtClass newClazz = cp.makeClass(clazz.getCanonicalName() + ".generated"
        + System.nanoTime());
    newClazz.setSuperclass(cp.get(clazz.getCanonicalName()));
    List<String> methodList = new ArrayList<String>();
    for (Entry<String, List<List<String>>> entry : tests.entrySet()) {
      String methodName = csvShortName + "_" + entry.getKey();
      CtMethod m = new CtMethod(CtClass.voidType, methodName, new CtClass[0],
          newClazz);
      methodList.add(methodName);
      m.setBody("launchTest(\"" + entry.getKey() + "\");");
      newClazz.addMethod(m);
    }
    generatedClazz = newClazz.toClass(GwtTestClassLoader.getInstance(), null);
    for (String methodName : methodList) {
      Method m = generatedClazz.getMethod(methodName);
      testMethods.add(m);
    }
  }

  private static <T> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
    T annotation = GwtTestReflectionUtils.getAnnotation(clazz, annotationClass);
    if (annotation == null) {
      throw new RuntimeException("Missing annotation \'@"
          + annotationClass.getSimpleName() + "\' on class ["
          + clazz.getCanonicalName() + "]");
    }

    return annotation;
  }

  private static File getDirectory(String path) throws IOException {
    File directory = new File(path);
    if (!directory.exists()) {
      throw new FileNotFoundException("Directory [" + path + "] does not exist");
    } else if (!directory.isDirectory()) {
      throw new IOException("A directory was expected for path [" + path
          + "] but a file has been found");
    }

    return directory;
  }

  public List<Method> getTestMethods() {
    Collections.sort(testMethods, new Comparator<Method>() {

      public int compare(Method o1, Method o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    return testMethods;
  }

  public Set<String> getTestList() {
    return Collections.unmodifiableSet(tests.keySet());
  }

  public Set<String> getMacroFileList() {
    return Collections.unmodifiableSet(macros.keySet());
  }

  public List<List<String>> getTest(String testName) {
    return tests.get(testName);
  }

  public List<List<String>> getMacroFile(String macroName) {
    return macros.get(macroName);
  }

  public Object createObject() throws InstantiationException,
      IllegalAccessException {
    return generatedClazz.newInstance();
  }

}
