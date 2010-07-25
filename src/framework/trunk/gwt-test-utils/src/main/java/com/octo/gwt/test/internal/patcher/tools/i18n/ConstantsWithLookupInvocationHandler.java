package com.octo.gwt.test.internal.patcher.tools.i18n;

import java.lang.reflect.Method;
import java.util.Properties;

import com.google.gwt.i18n.client.LocalizableResource;

public class ConstantsWithLookupInvocationHandler extends LocalizableResourcesInvocationHandler {

	public ConstantsWithLookupInvocationHandler(Class<? extends LocalizableResource> proxiedClass) {
		super(proxiedClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object extractFromProperties(Properties localizedProperties, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object extractDefaultValue(Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
