package com.octo.gwt.test.integ.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.PatchGwtClassPool;
import com.octo.gwt.test.integ.CsvDirectory;
import com.octo.gwt.test.integ.CsvMacros;
import com.octo.gwt.test.integ.csvrunner.CsvReader;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class DirectoryTestReader {

	private String csvDirectory;

	private Class<?> generatedClazz;

	private HashMap<String, List<List<String>>> tests;

	private HashMap<String, List<List<String>>> macroFiles;

	private List<Method> testMethods;

	public DirectoryTestReader(Class<?> clazz) {
		try {
			initCsvTests(clazz);
			initCsvMacros(clazz);
			initTestMethods(clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void initCsvTests(Class<?> clazz) throws FileNotFoundException, IOException {
		CsvDirectory csvDirectoryAnnotation = GwtTestReflectionUtils.getAnnotation(clazz, CsvDirectory.class);
		if (csvDirectoryAnnotation == null) {
			throw new RuntimeException("Missing annotation \'@CsvDirectory\' on class [" + clazz.getCanonicalName() + "]");
		}
		csvDirectory = csvDirectoryAnnotation.value();

		File directory = new File(this.csvDirectory);
		if (!directory.exists()) {
			throw new FileNotFoundException("Directory [" + this.csvDirectory + "] does not exist");
		} else if (!directory.isDirectory()) {
			throw new IOException("A directory was expected for path [" + csvDirectory + "] but a file has been found");
		}

		testMethods = new ArrayList<Method>();

		tests = new HashMap<String, List<List<String>>>();
		for (File f : directory.listFiles()) {
			if (f.getName().endsWith(".csv")) {
				String s = f.getName();
				s = s.substring(0, s.length() - 4);
				tests.put(s, CsvReader.readCsv(new FileReader(f)));
			}
		}
	}

	private void initCsvMacros(Class<?> clazz) throws FileNotFoundException, IOException {
		CsvMacros csvMacrosAnnotation = GwtTestReflectionUtils.getAnnotation(clazz, CsvMacros.class);
		if (csvMacrosAnnotation == null) {
			throw new RuntimeException("Missing annotation \'@CsvMacros\' on class [" + clazz.getCanonicalName() + "]");
		}

		Pattern macroNamePattern = (csvMacrosAnnotation.pattern() != null) ? Pattern.compile(csvMacrosAnnotation.pattern()) : null;
		File macrosDirectory = new File(csvMacrosAnnotation.directory());
		if (!macrosDirectory.exists()) {
			throw new FileNotFoundException("Directory [" + csvMacrosAnnotation.directory() + "] does not exist");
		} else if (!macrosDirectory.isDirectory()) {
			throw new IOException("A directory was expected for path [" + csvMacrosAnnotation.directory() + "] but a file has been found");
		}

		macroFiles = new HashMap<String, List<List<String>>>();
		for (File file : macrosDirectory.listFiles()) {
			String fileName = file.getName();
			if (macroNamePattern == null || macroNamePattern.matcher(file.getName()).matches()) {
				FileReader reader = new FileReader(file);
				List<List<String>> sheet = CsvReader.readCsv(reader);
				macroFiles.put(fileName, sheet);
			}
		}
	}

	private void initTestMethods(Class<?> clazz) throws Exception {
		testMethods = new ArrayList<Method>();
		String csvShortName = csvDirectory.substring(csvDirectory.lastIndexOf('/') + 1);
		ClassPool cp = PatchGwtClassPool.get();

		CtClass newClazz = cp.makeClass(clazz.getCanonicalName() + ".generated" + System.currentTimeMillis());
		newClazz.setSuperclass(cp.get(clazz.getCanonicalName()));
		List<String> methodList = new ArrayList<String>();
		for (Entry<String, List<List<String>>> entry : tests.entrySet()) {
			String methodName = "run_" + csvShortName + "_" + entry.getKey();
			CtMethod m = new CtMethod(CtClass.voidType, methodName, new CtClass[0], newClazz);
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

	public List<Method> getTestMethods() {
		return testMethods;
	}

	public Set<String> getTestList() {
		return Collections.unmodifiableSet(tests.keySet());
	}

	public Set<String> getMacroFileList() {
		return Collections.unmodifiableSet(macroFiles.keySet());
	}

	public List<List<String>> getTest(String testName) {
		return tests.get(testName);
	}

	public List<List<String>> getMacroFile(String macroName) {
		return macroFiles.get(macroName);
	}

	public Object createObject() throws InstantiationException, IllegalAccessException {
		return generatedClazz.newInstance();
	}

}
