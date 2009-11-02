package com.octo.gwt.test17.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Properties;

public final class Startup {
	public static Instrumentation instrumentation;

	public static void premain(String agentArgs, Instrumentation inst) throws IOException {
		initialize(agentArgs, inst);
	}

	public static void agentmain(String agentArgs, Instrumentation inst) throws IOException {
		initialize(agentArgs, inst);
	}

	private static void initialize(String agentArgs, Instrumentation inst) throws IOException {
		instrumentation = inst;
	}

	public static void redefineClass(Class<?> clazz, byte[] byteCode) throws ClassNotFoundException, UnmodifiableClassException {
		if (instrumentation == null) {
			throw new RuntimeException("Please add -javaagent:bootstrap.jar on jvm command line");
		}
		instrumentation.redefineClasses(new ClassDefinition[] { new ClassDefinition(clazz, byteCode) });
	}
	
	public static Properties loadProperties(InputStream inputStream, String charset) throws IOException {
		Properties properties = new Properties();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
		properties.load(inputStreamReader);
		return properties;
	}
}
