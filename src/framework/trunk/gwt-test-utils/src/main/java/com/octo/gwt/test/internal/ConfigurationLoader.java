package com.octo.gwt.test.internal;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLDecoder;
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
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import com.octo.gwt.test.IPatcher;
import com.octo.gwt.test.internal.modifiers.ClassSubstituer;
import com.octo.gwt.test.internal.modifiers.JavaClassModifier;
import com.octo.gwt.test.internal.modifiers.MethodRemover;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class ConfigurationLoader {

	private static final Logger logger = Logger.getLogger(ConfigurationLoader.class);

	private static final String CONFIG_FILENAME = "META-INF/gwt-test-utils.properties";

	private static ConfigurationLoader INSTANCE;

	private List<JavaClassModifier> javaClassModifierList;

	private MethodRemover methodRemover;

	public static final ConfigurationLoader createInstance(ClassLoader classLoader) {
		if (INSTANCE != null) {
			throw new RuntimeException(ConfigurationLoader.class.getSimpleName() + " instance has already been initialized");
		}

		INSTANCE = new ConfigurationLoader(classLoader);

		return INSTANCE;
	}

	public static ConfigurationLoader getInstance() {
		if (INSTANCE == null) {
			throw new RuntimeException(ConfigurationLoader.class.getSimpleName() + " instance has not been initialized yet");
		}

		return INSTANCE;
	}

	private ClassLoader classLoader;
	private List<String> delegateList;
	private List<String> notDelegateList;
	private Set<String> scanPackageSet;
	private Map<String, IPatcher> patchers;

	private ConfigurationLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
		delegateList = new ArrayList<String>();
		notDelegateList = new ArrayList<String>();
		javaClassModifierList = new ArrayList<JavaClassModifier>();
		methodRemover = new MethodRemover();
		javaClassModifierList.add(methodRemover);
		scanPackageSet = new HashSet<String>();

		readFiles();
	}

	public Map<String, IPatcher> getPatchers() {
		if (patchers == null) {
			try {
				loadPatchers();
			} catch (Exception e) {
				throw new RuntimeException("Error while loading " + IPatcher.class.getSimpleName() + " instances", e);
			}
		}

		return patchers;
	}

	private void loadPatchers() throws Exception {
		patchers = new HashMap<String, IPatcher>();
		List<String> classList = findScannedClasses();
		for (String className : classList) {
			Class<?> clazz = Class.forName(className, true, classLoader);

			PatchClass patchClass = GwtTestReflectionUtils.getAnnotation(clazz, PatchClass.class);

			if (patchClass == null) {
				continue;
			}

			if (!IPatcher.class.isAssignableFrom(clazz) || !hasDefaultConstructor(clazz)) {
				throw new RuntimeException("The @" + PatchClass.class.getSimpleName() + " annotated class '" + clazz.getName() + "' must implements "
						+ IPatcher.class.getSimpleName() + " interface and provide an empty constructor");
			}

			IPatcher patcher = (IPatcher) clazz.newInstance();

			for (Class<?> c : patchClass.value()) {
				String targetName = c.isMemberClass() ? c.getDeclaringClass().getCanonicalName() + "$" + c.getSimpleName() : c.getCanonicalName();
				if (patchers.get(targetName) != null) {
					logger.error("Two patches for same class " + targetName);
					throw new RuntimeException("Two patches for same class " + targetName);
				}
				patchers.put(targetName, patcher);
				logger.debug("Add patch for class " + targetName + " : " + clazz.getCanonicalName());
			}
			for (String s : patchClass.classes()) {
				if (patchers.get(s) != null) {
					logger.error("Two patches for same class " + s);
					throw new RuntimeException("Two patches for same class " + s);
				}
				patchers.put(s, patcher);
				logger.debug("Add patch for class " + s + " : " + clazz.getCanonicalName());
			}
		}
	}

	private boolean hasDefaultConstructor(Class<?> clazz) {
		for (Constructor<?> cons : clazz.getConstructors()) {
			if (cons.getParameterTypes().length == 0) {
				cons.setAccessible(true);
				return true;
			}

		}

		return false;
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
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new RuntimeException("Error while reading '" + CONFIG_FILENAME + "' files", e);
		}
	}

	private void process(Properties p, URL url) {
		for (Entry<Object, Object> entry : p.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if ("scan-package".equals(value)) {
				if (!scanPackageSet.add(key)) {
					throw new RuntimeException("'scan-package' mechanism is used to scan the same package twice : '" + key + "'");
				}
			} else if ("delegate".equals(value)) {
				delegateList.add(key);
			} else if ("notDelegate".equals(value)) {
				notDelegateList.add(key);
			} else if ("remove-method".equals(value)) {
				processRemoveMethod(key, url);
			} else if ("substitute-class".equals(value)) {
				processSubstituteClass(key, url);
			} else {
				throw new RuntimeException("Error in '" + url.getPath() + "' : unknown value '" + value + "'");
			}
		}
	}

	private void processSubstituteClass(String key, URL url) {
		String[] array = key.split("\\s*,\\s*");
		if (array.length != 2) {
			throw new RuntimeException("Invalid 'substitute-class' property format for value '" + key + "' in file '" + url.getPath() + "'");
		}

		String originalClass = array[0];
		String substitutionClass = array[1];
		javaClassModifierList.add(new ClassSubstituer(originalClass, substitutionClass));
	}

	private void processRemoveMethod(String key, URL url) {
		String[] array = key.split("\\s*,\\s*");
		if (array.length != 3) {
			throw new RuntimeException("Invalid 'remove-method' property format for value '" + key + "' in file '" + url.getPath() + "'");
		}

		String methodClass = array[0];
		String methodName = array[1];
		String methodSignature = array[2];

		methodRemover.removeMethod(methodClass, methodName, methodSignature);
	}

	public List<JavaClassModifier> getJavaClassModifierList() {
		return Collections.unmodifiableList(javaClassModifierList);
	}

	public List<String> getDelegateList() {
		return Collections.unmodifiableList(delegateList);
	}

	public List<String> getNotDelegateList() {
		return Collections.unmodifiableList(notDelegateList);
	}

	private List<String> findScannedClasses() throws Exception {
		List<String> classList = new ArrayList<String>();
		for (String s : scanPackageSet) {
			String path = s.replaceAll("\\.", "/");
			logger.debug("Scan package " + s);
			Enumeration<URL> l = classLoader.getResources(path);
			while (l.hasMoreElements()) {
				URL url = l.nextElement();
				String u = url.toExternalForm();
				if (u.startsWith("file:")) {
					String directoryName = u.substring("file:".length());
					directoryName = URLDecoder.decode(directoryName, "UTF-8");
					loadClassesFromDirectory(new File(directoryName), s, classList);
				} else if (u.startsWith("jar:file:")) {
					loadClassesFromJarFile(u.substring("jar:file:".length()), s, classList);
				} else {
					throw new RuntimeException("Not managed class container " + u);
				}
			}
		}
		return classList;
	}

	private void loadClassesFromJarFile(String path, String s, List<String> classList) throws Exception {
		String prefix = path.substring(path.indexOf("!") + 2);
		String jarName = path.substring(0, path.indexOf("!"));
		jarName = URLDecoder.decode(jarName, "UTF-8");
		logger.debug("Load classes from jar " + jarName);
		JarFile jar = new JarFile(jarName);
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.getName().startsWith(prefix) && entry.getName().endsWith(".class")) {
				String className = entry.getName();
				className = className.substring(0, className.length() - ".class".length());
				className = className.replaceAll("\\/", ".");
				classList.add(className);
			}
		}
		logger.debug("Classes loaded from jar " + jarName);
	}

	private void loadClassesFromDirectory(File directoryToScan, String current, List<String> classList) {
		logger.debug("Scan directory " + directoryToScan);
		for (File f : directoryToScan.listFiles()) {
			if (f.isDirectory()) {
				if (!".".equals(f.getName()) && !"..".equals(f.getName())) {
					loadClassesFromDirectory(f, current + "." + f.getName(), classList);
				}
			} else {
				if (f.getName().endsWith(".class")) {
					classList.add(current + "." + f.getName().substring(0, f.getName().length() - ".class".length()));
				}
			}
		}
		logger.debug("Directory scanned " + directoryToScan);
	}

}
