package com.octo.gwt.test;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.octo.gwt.test.internal.patcher.tools.PatchClass;

public class ConfigurationLoader {

	private static final String CONFIG_FILENAME = "META-INF/gwt-test-utils.properties";

	private ClassLoader classLoader;

	private List<String> delegateList;

	private List<String> notDelegateList;

	private List<String> scanList;

	public ConfigurationLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
		this.delegateList = new ArrayList<String>();
		this.notDelegateList = new ArrayList<String>();
		this.scanList = new ArrayList<String>();
	}

	public void readFiles() throws Exception {
		Enumeration<URL> l = classLoader.getResources(CONFIG_FILENAME);
		while (l.hasMoreElements()) {
			URL url = l.nextElement();
			Properties p = new Properties();
			InputStream inputStream = url.openStream();
			p.load(inputStream);
			inputStream.close();
			process(p);
		}
	}

	private void process(Properties p) {
		for (Entry<Object, Object> entry : p.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if ("scan-package".equals(value)) {
				scanList.add(key);
			} else if ("delegate".equals(value)) {
				delegateList.add(key);
			} else if ("notDelegate".equals(value)) {
				notDelegateList.add(key);
			} else {
				throw new RuntimeException(
						"Wrong gwt-test-utils.properties : unknown value "
								+ value);
			}
		}
	}

	public List<String> getDelegateList() {
		return Collections.unmodifiableList(delegateList);
	}

	public List<String> getNotDelegateList() {
		return Collections.unmodifiableList(notDelegateList);
	}

	public List<String> findScannedClasses() throws Exception {
		List<String> classList = new ArrayList<String>();
		for (String s : scanList) {
			String path = s.replaceAll("\\.", "/");
			Enumeration<URL> l = classLoader.getResources(path);
			while (l.hasMoreElements()) {
				URL url = l.nextElement();
				String u = url.toExternalForm();
				if (u.startsWith("file:")) {
					loadClassesFromDirectory(new File(u.substring("file:"
							.length())), s, classList);
				} else if (u.startsWith("jar:file:")) {
					loadClassesFromJarFile(u.substring("jar:file:".length()),
							s, classList);
				} else {
					throw new RuntimeException("Not managed class container "
							+ u);
				}
			}
		}
		return classList;
	}

	private void loadClassesFromJarFile(String path, String s, List<String> classList) throws Exception {
		String jarName = path.substring(0, path.indexOf("!"));
		String prefix = path.substring(path.indexOf("!") + 2);
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
	}

	private void loadClassesFromDirectory(File directoryToScan, String current,
			List<String> classList) {
		for (File f : directoryToScan.listFiles()) {
			if (f.isDirectory()) {
				if (!".".equals(f.getName()) && !"..".equals(f.getName())) {
					loadClassesFromDirectory(f, current + "." + f.getName(),
							classList);
				}
			} else {
				if (f.getName().endsWith(".class")) {
					classList.add(current
							+ "."
							+ f.getName().substring(0,
									f.getName().length() - ".class".length()));
				}
			}
		}
	}

	public Map<String, IPatcher> fillPatchList(List<String> classList)
			throws Exception {
		Map<String, IPatcher> map = new HashMap<String, IPatcher>();
		for (String className : classList) {
			Class<?> clazz = Class.forName(className, true, classLoader);
			for (Annotation a : clazz.getDeclaredAnnotations()) {
				if (a.annotationType() == PatchClass.class) {
					PatchClass patchClass = (PatchClass) a;
					IPatcher patcher = (IPatcher) clazz.newInstance();
					for (Class<?> c : patchClass.value()) {
						String targetName = c.isMemberClass() ? c
								.getDeclaringClass().getCanonicalName()
								+ "$" + c.getSimpleName() : c
								.getCanonicalName();
						if (map.get(targetName) != null) {
							throw new RuntimeException(
									"Two patches for same class " + targetName);
						}
						map.put(targetName, patcher);
					}
					for (String s : patchClass.classes()) {
						if (map.get(s) != null) {
							throw new RuntimeException(
									"Two patches for same class " + s);
						}
						map.put(s, patcher);
					}
				}
			}
		}
		return map;
	}

}
