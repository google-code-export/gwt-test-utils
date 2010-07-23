package com.octo.gwt.test.internal.patcher.tools.i18n;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.octo.gwt.test.PatchGwtConfig;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.PatchGwtUtils;

public abstract class LocalizableResourcesInvocationHandler implements InvocationHandler {

	private Class<? extends LocalizableResource> proxiedClass;
	private Map<String, Properties> localizedProperties = new HashMap<String, Properties>();

	public LocalizableResourcesInvocationHandler(Class<? extends LocalizableResource> proxiedClass) {
		this.proxiedClass = proxiedClass;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Locale locale = getResourceLocale(proxiedClass);
		Properties prop = localizedProperties.get(locale.toString());
		if (prop == null) {
			prop = PatchGwtUtils.getLocalizedProperties(proxiedClass.getCanonicalName().replaceAll("\\.", "/"), locale);
			localizedProperties.put(locale.toString(), prop);
		}
		return extractFromProperties(proxiedClass, prop, method, args);
	}

	protected abstract Object extractFromProperties(Class<? extends LocalizableResource> clazz, Properties localizedProperties, Method method,
			Object[] args) throws Throwable;

	private static Locale getResourceLocale(Class<?> clazz) {
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
			return PatchGwtConfig.getLocale();
		}
	}

}
