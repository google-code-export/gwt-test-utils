package com.octo.gwt.test.internal.patcher.tools.i18n;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource;

public class LocalizableResourceProxyFactory {

	private static Map<String, LocalizableResourceProxyFactory> factoryMap = new HashMap<String, LocalizableResourceProxyFactory>();

	private Class<? extends LocalizableResource> proxiedClass;

	private LocalizableResourceProxyFactory(Class<? extends LocalizableResource> proxiedClass) {
		this.proxiedClass = proxiedClass;
	}

	public static <T extends LocalizableResource> LocalizableResourceProxyFactory getFactory(Class<T> clazz) {
		LocalizableResourceProxyFactory factory = factoryMap.get(clazz.getName());
		if (factory == null) {
			factory = new LocalizableResourceProxyFactory(clazz);
			factoryMap.put(clazz.getName(), factory);
		}

		return factory;
	}

	@SuppressWarnings("unchecked")
	public <T extends LocalizableResource> T createProxy() {
		InvocationHandler ih = createInvocationHandler(proxiedClass);
		return (T) Proxy.newProxyInstance(proxiedClass.getClassLoader(), new Class<?>[] { proxiedClass }, ih);
	}

	@SuppressWarnings("unchecked")
	private InvocationHandler createInvocationHandler(Class<? extends LocalizableResource> clazz) {
		if (Constants.class.isAssignableFrom(clazz)) {
			return new ConstantsInvocationHandler((Class<? extends Constants>) clazz);
		} else {
			throw new RuntimeException("Not managed GWT i18n interface for testing : " + clazz.getSimpleName());
		}
	}

}
