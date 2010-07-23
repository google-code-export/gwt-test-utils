package com.octo.gwt.test.internal.patcher.tools.i18n;

import java.lang.reflect.Method;
import java.util.Properties;

import com.google.gwt.i18n.client.Constants.DefaultStringValue;
import com.google.gwt.i18n.client.LocalizableResource;
import com.octo.gwt.test.utils.PatchGwtUtils;
import com.octo.gwt.test.utils.PatchGwtUtils.SequenceReplacement;

public class ConstantsInvocationHandler extends LocalizableResourcesInvocationHandler {

	public ConstantsInvocationHandler(Class<? extends LocalizableResource> proxiedClass) {
		super(proxiedClass);
	}

	@Override
	protected Object extractFromProperties(Class<? extends LocalizableResource> clazz, Properties properties, Method method, Object[] args)
			throws Throwable {
		String line = null;
		if (properties != null) {
			line = properties.getProperty(method.getName());
		}
		if (line == null) {
			DefaultStringValue v = method.getAnnotation(DefaultStringValue.class);
			if (v == null) {
				throw new UnsupportedOperationException("No matching property \"" + method.getName() + "\" for Constants class ["
						+ clazz.getCanonicalName() + "]. Please check the corresponding properties file or use @"
						+ DefaultStringValue.class.getSimpleName());
			}

			line = v.value();
		}

		for (SequenceReplacement sr : PatchGwtUtils.sequenceReplacementList) {
			line = sr.treat(line);
		}

		if (method.getReturnType() == String.class) {
			return line;
		}
		String[] result = line.split("\\s*,\\s*");
		return result;
	}
}
