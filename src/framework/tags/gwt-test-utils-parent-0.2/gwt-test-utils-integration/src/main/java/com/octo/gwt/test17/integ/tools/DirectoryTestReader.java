package com.octo.gwt.test17.integ.tools;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.integ.CsvDirectory;
import com.octo.gwt.test17.integ.CsvMacros;
import com.octo.gwt.test17.integ.csvrunner.CsvReader;

public class DirectoryTestReader {

	private String csvDirectory;

	private String csvShortName;

	private List<String> csvMacros;

	private Class<?> generatedClazz;

	private HashMap<String, List<List<String>>> tests;

	private HashMap<String, List<List<String>>> macroFiles;
	
	private List<Method> testMethods;

	public DirectoryTestReader(Class<?> clazz) {
		CsvDirectory csvDirectoryAnnotation = ReflectionUtils.getAnnotation(clazz, CsvDirectory.class);
		if (csvDirectoryAnnotation == null) {
			throw new RuntimeException("Missing annotation \'@CsvDirectory\' on class [" + clazz.getCanonicalName() + "]");
		}
		csvDirectory = csvDirectoryAnnotation.value();
		csvShortName = csvDirectory.substring(csvDirectory.lastIndexOf('/') + 1);
		CsvMacros csvMacrosAnnotation = ReflectionUtils.getAnnotation(clazz, CsvMacros.class);
		if (csvMacrosAnnotation == null) {
			throw new RuntimeException("Missing annotation \'@CsvMacros\' on class [" + clazz.getCanonicalName() + "]");
		}
		this.csvMacros = new ArrayList<String>();
		for(String s : csvMacrosAnnotation.value()) {
			csvMacros.add(s);
		}
		File directory = new File(this.csvDirectory);
		if (!directory.exists()) {
			throw new RuntimeException("Firectory [" + this.csvDirectory + "] does not exist");
		}
	
		testMethods = new ArrayList<Method>();

		try {
			tests = new HashMap<String, List<List<String>>>();
			macroFiles = new HashMap<String, List<List<String>>>();
			for (File f : directory.listFiles()) {
				if (f.getName().endsWith(".csv")) {
					String s = f.getName();
					s = s.substring(0, s.length() - 4);
					FileReader reader = new FileReader(f);
					List<List<String>> sheet = CsvReader.readCsv(reader);
					if (csvMacros.contains(s)) {
						macroFiles.put(s, sheet);
					} else {
						tests.put(s, sheet);
					}
				}
			}

			ClassPool cp = ClassPool.getDefault();
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
			generatedClazz = newClazz.toClass();
			for (String methodName : methodList) {
				Method m = generatedClazz.getMethod(methodName);
				testMethods.add(m);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
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
