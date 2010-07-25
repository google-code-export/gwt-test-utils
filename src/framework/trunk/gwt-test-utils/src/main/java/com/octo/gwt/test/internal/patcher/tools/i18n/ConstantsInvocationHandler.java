package com.octo.gwt.test.internal.patcher.tools.i18n;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.google.gwt.i18n.client.Constants.DefaultBooleanValue;
import com.google.gwt.i18n.client.Constants.DefaultDoubleValue;
import com.google.gwt.i18n.client.Constants.DefaultFloatValue;
import com.google.gwt.i18n.client.Constants.DefaultIntValue;
import com.google.gwt.i18n.client.Constants.DefaultStringArrayValue;
import com.google.gwt.i18n.client.Constants.DefaultStringMapValue;
import com.google.gwt.i18n.client.Constants.DefaultStringValue;
import com.google.gwt.i18n.client.LocalizableResource;
import com.octo.gwt.test.utils.PatchGwtUtils;
import com.octo.gwt.test.utils.PatchGwtUtils.SequenceReplacement;

public class ConstantsInvocationHandler extends LocalizableResourcesInvocationHandler {

	public ConstantsInvocationHandler(Class<? extends LocalizableResource> proxiedClass) {
		super(proxiedClass);
	}

	@Override
	protected Object extractFromProperties(Properties properties, Method method, Object[] args) throws Throwable {
		String line = properties.getProperty(method.getName());

		if (line == null) {
			return null;
		}

		line = treatLine(line);

		Class<?> returnType = method.getReturnType();

		if (returnType == String.class) {
			return line;
		} else if (returnType.isArray() && returnType.getComponentType() == String.class) {
			return line.split("\\s*,\\s*");
		} else if (returnType == Map.class) {
			Class<?> clazz = method.getDeclaringClass();
			Map<String, Object> result = new HashMap<String, Object>();
			String[] v = line.split("\\s*,\\s*");
			for (int i = 0; i < v.length; i++) {
				String methodName = v[i];
				Method correspondingKeyMethod = clazz.getMethod(methodName);
				if (correspondingKeyMethod == null) {
					throw new RuntimeException("Cannot find method '" + methodName + "' in class [" + clazz.getName() + "]");
				}

				result.put(methodName, invoke(null, correspondingKeyMethod, null));
			}

			return result;
		} else if (returnType == Integer.class || returnType == Integer.TYPE) {
			return Integer.parseInt(line);
		} else if (returnType == Double.class || returnType == Double.TYPE) {
			return Double.parseDouble(line);
		} else if (returnType == Float.class || returnType == Float.TYPE) {
			return Float.parseFloat(line);
		} else if (returnType == Boolean.class || returnType == Boolean.TYPE) {
			return Boolean.parseBoolean(line);
		}

		throw new RuntimeException("The return type (" + returnType.getSimpleName() + ") of i18n '" + method.getDeclaringClass().getSimpleName()
				+ "." + method.getName() + "()' is not managed");

	}

	@Override
	protected Object extractDefaultValue(Method method, Object[] args) throws Throwable {
		Class<?> clazz = method.getDeclaringClass();
		Class<?> returnType = method.getReturnType();
		if (returnType == String.class) {
			DefaultStringValue a = getCheckedAnnotation(clazz, method, DefaultStringValue.class);
			return treatLine(a.value());
		} else if (returnType.isArray() && returnType.getComponentType() == String.class) {
			DefaultStringArrayValue a = getCheckedAnnotation(clazz, method, DefaultStringArrayValue.class);
			String[] v = a.value();
			for (int i = 0; i < v.length; i++) {
				v[i] = treatLine(v[i]);
			}
			return v;
		} else if (returnType == Map.class) {
			DefaultStringMapValue a = getCheckedAnnotation(clazz, method, DefaultStringMapValue.class);
			String[] v = a.value();
			Map<String, String> result = new HashMap<String, String>();
			for (int i = 0; i < v.length; i++) {
				String methodName = v[i];
				Method correspondingKeyMethod = clazz.getMethod(methodName);
				if (correspondingKeyMethod == null) {
					throw new RuntimeException("Cannot find method '" + methodName + "' in class [" + clazz.getName() + "]");
				} else if (correspondingKeyMethod.getReturnType() != String.class) {
					throw new RuntimeException("Method '" + clazz.getSimpleName() + "." + methodName + "()' should return a String");
				}

				result.put(methodName, (String) extractDefaultValue(correspondingKeyMethod, null));
			}

			return result;
		} else if (returnType == Integer.class || returnType == Integer.TYPE) {
			DefaultIntValue a = getCheckedAnnotation(clazz, method, DefaultIntValue.class);
			return a.value();
		} else if (returnType == Double.class || returnType == Double.TYPE) {
			DefaultDoubleValue a = getCheckedAnnotation(clazz, method, DefaultDoubleValue.class);
			return a.value();
		} else if (returnType == Float.class || returnType == Float.TYPE) {
			DefaultFloatValue a = getCheckedAnnotation(clazz, method, DefaultFloatValue.class);
			return a.value();
		} else if (returnType == Boolean.class || returnType == Boolean.TYPE) {
			DefaultBooleanValue a = getCheckedAnnotation(clazz, method, DefaultBooleanValue.class);
			return a.value();
		}

		throw new RuntimeException("The return type (" + returnType.getSimpleName() + ") of i18n '" + clazz.getSimpleName() + "." + method.getName()
				+ "()' is not managed");

	}

	private String treatLine(String line) {
		for (SequenceReplacement sr : PatchGwtUtils.sequenceReplacementList) {
			line = sr.treat(line);
		}

		return line;
	}

	private <T extends Annotation> T getCheckedAnnotation(Class<?> clazz, Method method, Class<T> defaultAnnotation) {
		T v = method.getAnnotation(defaultAnnotation);
		if (v == null) {
			throw new RuntimeException("No matching property \"" + method.getName() + "\" for Constants class [" + clazz.getCanonicalName()
					+ "]. Please check the corresponding properties file or use @" + defaultAnnotation.getSimpleName());
		}

		return v;
	}
}
