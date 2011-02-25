package com.octo.gwt.test.internal.patcher.tools.i18n;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Properties;

import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.octo.gwt.test.PatchGwtConfig;
import com.octo.gwt.test.internal.utils.PatchGwtUtils;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public abstract class LocalizableResourcesInvocationHandler implements InvocationHandler {

	private Class<? extends LocalizableResource> proxiedClass;

	public LocalizableResourcesInvocationHandler(Class<? extends LocalizableResource> proxiedClass) {
		this.proxiedClass = proxiedClass;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Locale locale = getResourceLocale(proxiedClass);
		Properties prop = PatchGwtUtils.getLocalizedProperties(proxiedClass.getCanonicalName().replaceAll("\\.", "/"), locale);

		Object result = null;
		if (prop != null) {
			result = extractFromProperties(prop, method, args, locale);
		}

		if (result != null) {
			return result;
		}

		result = extractDefaultValue(method, args, locale);

		if (result != null) {
			return result;
		}

		throw new RuntimeException("Unable to find a Locale specific resource file to bind with i18n interface '" + proxiedClass.getName()
				+ "' and there is no @DefaultXXXValue annotation on '" + method.getName() + "' called method");
	}

	protected abstract Object extractFromProperties(Properties localizedProperties, Method method, Object[] args, Locale locale) throws Throwable;

	protected abstract Object extractDefaultValue(Method method, Object[] args, Locale locale) throws Throwable;

	private Locale getResourceLocale(Class<?> clazz) {
		if (PatchGwtConfig.get().getLocale() != null) {
			return PatchGwtConfig.get().getLocale();
		}

		DefaultLocale annotation = GwtTestReflectionUtils.getAnnotation(clazz, DefaultLocale.class);
		if (annotation != null) {
			String[] localeCodes = annotation.value().split("_");
			switch (localeCodes.length) {
			case 1:
				return new Locale(localeCodes[0]);
			case 2:
				return new Locale(localeCodes[0], localeCodes[1]);
			default:
				throw new RuntimeException("Cannot parse Locale value in annoted class [" + clazz.getSimpleName() + "] : @"
						+ DefaultLocale.class.getSimpleName() + "(" + annotation.value() + ")");
			}
		} else {
			return null;
		}
	}

	public Class<? extends LocalizableResource> getProxiedClass() {
		return proxiedClass;
	}

	protected String extractProperty(Properties properties, String key) {
		String result = properties.getProperty(key);
		if (result == null) {
			result = properties.getProperty(key.replaceAll("_", "."));
		}
		return result;
	}

}
