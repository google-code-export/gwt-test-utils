package com.octo.gwt.test.internal;

import java.io.File;
import java.io.InputStream;
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

import javassist.CtClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.Patcher;
import com.octo.gwt.test.internal.modifiers.ClassSubstituer;
import com.octo.gwt.test.internal.modifiers.JavaClassModifier;
import com.octo.gwt.test.internal.modifiers.MethodRemover;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class ConfigurationLoader {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationLoader.class);

	private static final String CONFIG_FILENAME = "META-INF/gwt-test-utils.properties";

	private static final String JSO_CLASSNAME = "com.google.gwt.core.client.JavaScriptObject";

	private static ConfigurationLoader INSTANCE;

	public static final ConfigurationLoader createInstance(ClassLoader classLoader) {
		if (INSTANCE != null) {
			throw new RuntimeException(ConfigurationLoader.class.getSimpleName() + " instance has already been initialized");
		}

		INSTANCE = new ConfigurationLoader(classLoader);

		return INSTANCE;
	}

	public static ConfigurationLoader get() {
		if (INSTANCE == null) {
			throw new RuntimeException(ConfigurationLoader.class.getSimpleName() + " instance has not been initialized yet");
		}

		return INSTANCE;
	}

	private ClassLoader classLoader;
	private List<String> delegateList;
	private Set<String> scanPackageSet;
	private Set<String> jsoSubClasses;
	private Map<String, Patcher> patchers;
	private List<JavaClassModifier> javaClassModifierList;
	private MethodRemover methodRemover;

	private ConfigurationLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
		delegateList = new ArrayList<String>();
		scanPackageSet = new HashSet<String>();
		javaClassModifierList = new ArrayList<JavaClassModifier>();
		methodRemover = new MethodRemover();
		javaClassModifierList.add(methodRemover);

		readFiles();
		try {
			loadPatchersAndJavaScriptObjects();
		} catch (Exception e) {
			throw new RuntimeException("Error while loading " + JavaScriptObject.class.getSimpleName() + " subclass and "
					+ Patcher.class.getSimpleName() + " instances");
		}
	}

	public Map<String, Patcher> getPatchers() {
		return patchers;
	}

	public Set<String> getJSOSubClasses() {
		return jsoSubClasses;
	}

	public List<JavaClassModifier> getJavaClassModifierList() {
		return Collections.unmodifiableList(javaClassModifierList);
	}

	public List<String> getDelegateList() {
		return Collections.unmodifiableList(delegateList);
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
			} else if ("remove-method".equals(value)) {
				processRemoveMethod(key, url);
			} else if ("substitute-class".equals(value)) {
				processSubstituteClass(key, url);
			} else if ("class-modifier".equals(value)) {
				processClassModifier(key, url);
			} else if ("module-file".equals(value)) {
				processModuleFile(key, url);
			} else {
				throw new RuntimeException("Error in '" + url.getPath() + "' : unknown value '" + value + "'");
			}
		}
	}

	private void processModuleFile(String key, URL url) {
		ModuleData.get().parseModule(key);
	}

	private void processSubstituteClass(String key, URL url) {
		String[] array = key.split("\\s*,\\s*");
		if (array.length != 2) {
			throw new RuntimeException("Invalid 'substitute-class' property format for value '" + key + "' in file '" + url.getPath() + "'");
		}

		String originalClass = array[0];
		String substitutionClass = array[1];
		// add in second position, just after method-substituer
		javaClassModifierList.add(1, new ClassSubstituer(originalClass, substitutionClass));
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

	private void processClassModifier(String key, URL url) {
		Class<?> clazz = null;

		// get the class
		try {
			clazz = Class.forName(key);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Invalid 'class-modifier' property : " + key + " class cannot be found");
		}

		// check it implements JavaClassModifier
		if (!JavaClassModifier.class.isAssignableFrom(clazz)) {
			throw new RuntimeException("Invalid 'class-modifier' property : " + key + " is not implementing "
					+ JavaClassModifier.class.getSimpleName() + " interface");
		}

		// Instanciate it and add it to the list of JavaClassModifier
		try {
			JavaClassModifier modifier = (JavaClassModifier) clazz.newInstance();
			javaClassModifierList.add(modifier);
		} catch (Exception e) {
			throw new RuntimeException("Error while instanciating " + key + " through the default constructor", e);
		}
	}

	private void loadPatchersAndJavaScriptObjects() throws Exception {
		jsoSubClasses = new HashSet<String>();
		patchers = new HashMap<String, Patcher>();
		List<String> classList = findScannedClasses();
		CtClass jsoCtClass = GwtClassPool.get().get(JSO_CLASSNAME);
		for (String className : classList) {
			CtClass ctClass = GwtClassPool.get().get(className);
			if (ctClass.subclassOf(jsoCtClass) && !ctClass.equals(jsoCtClass)) {
				jsoSubClasses.add(className);
			} else if (ctClass.hasAnnotation(PatchClass.class)) {

				treatPatchClass(ctClass);
			}
		}
	}

	private void treatPatchClass(CtClass patchCtClass) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(patchCtClass.getName(), true, classLoader);
		PatchClass patchClass = GwtReflectionUtils.getAnnotation(clazz, PatchClass.class);

		if (!Patcher.class.isAssignableFrom(clazz) || !hasDefaultConstructor(clazz)) {
			throw new RuntimeException("The @" + PatchClass.class.getSimpleName() + " annotated class '" + clazz.getName() + "' must implements "
					+ Patcher.class.getSimpleName() + " interface and provide an empty constructor");
		}

		Patcher patcher = (Patcher) GwtReflectionUtils.instantiateClass(clazz);

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

	private boolean hasDefaultConstructor(Class<?> clazz) {
		try {
			return clazz.getDeclaredConstructor() != null;
		} catch (NoSuchMethodException e) {
			return false;
		}
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
